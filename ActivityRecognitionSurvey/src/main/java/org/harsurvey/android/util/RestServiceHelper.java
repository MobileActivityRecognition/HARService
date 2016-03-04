/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.harsurvey.android.util;

import android.content.ContentUris;
import android.os.AsyncTask;
import android.os.Debug;
import android.util.Log;

import com.google.gson.Gson;

import org.harsurvey.android.data.FeatureData;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.survey.R;
import org.harsurvey.android.survey.SurveyApplication;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Rest service tu upload data
 */
public class RestServiceHelper {
    public static final String TAG = RestServiceHelper.class.getSimpleName();
    private final SurveyApplication context;
    private final String endpoint;
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    public RestServiceHelper(SurveyApplication application) {
        context = application;
        endpoint = String.format(Constants.REST_URL, Constants.getStringResource(context,
                R.string.rest_host));
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"));
        headers.setAcceptEncoding(ContentCodingType.GZIP);
        headers.setAccept(Collections.singletonList(new MediaType("application", "json")));
    }

    public void saveFeatureData(HumanActivityData activity, List<FeatureData> featureSet) {
        Map<String, Object> data = new TreeMap<>();

        data.put("imei", context.getPreference().getIMEI());
        data.put("nombre", context.getPreference().getName());
        data.put("phoneName", context.getPreference().getPhoneName());
        data.put("sensorName", context.getPreference().getSensorName());

        List<Map> content = new ArrayList<>();
        for (FeatureData featureData : featureSet) {
            content.add(MappingHelper.fromActivityToJson(activity, featureData));
        }

        data.put("collaborativefeatureList", content);
        Log.d(TAG, MappingHelper.toJson(data));
        HttpRequestTask task = new HttpRequestTask();
        task.execute(context.getPreference().getIMEI(), data, activity);
    }

    private class HttpRequestTask extends AsyncTask<Object, Void, HumanActivityData> {

        @Override
        protected HumanActivityData doInBackground(Object... payloads) {
            String imei = (String) payloads[0];
            HttpEntity<Object> request = new HttpEntity<>(payloads[1], headers);
            ResponseEntity<String> result = null;
            try {
                result = restTemplate.exchange(endpoint + "/" + imei,
                        HttpMethod.PUT, request, String.class);
                return ((HumanActivityData) payloads[2]);
            } catch (RestClientException e) {
                Log.e(TAG, "Error al procesar los datos: " + e.getLocalizedMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(HumanActivityData activity) {
            if (activity != null) {
                activity.status = HumanActivityData.Status.SAVED;
                context.getContentResolver().update(ContentUris.withAppendedId(HumanActivityData.CONTENT_URI,
                        activity.getId()), activity.getValues(), null, null);
            }
        }


    }
}
