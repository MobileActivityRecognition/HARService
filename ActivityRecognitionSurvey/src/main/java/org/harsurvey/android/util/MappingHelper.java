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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.harsurvey.android.data.FeatureData;
import org.harsurvey.android.data.HumanActivityData;

import java.util.Map;
import java.util.TreeMap;

/**
 * Maps Activity and Features to JSON
 */
public class MappingHelper {
    private static final Gson MAPPER = new GsonBuilder().setDateFormat(Constants.DATETIME_FORMAT).create();
    ;
    public static final String toJson(Object obj) {
        return MAPPER.toJson(obj);
    }

    public static Map<String, Object> fromActivityToJson(HumanActivityData activity,
                                                         FeatureData featureData) {
        TreeMap<String, Object> map = new TreeMap<>();
        String jsonDate = toJson(activity.created).replace("\"", "");
        int lastTwo = jsonDate.length() - 2;
        map.put("arX1", featureData.ar1);
        map.put("arX2", featureData.ar2);
        map.put("arX3", featureData.ar3);
        map.put("arX4", featureData.ar4);
        map.put("energyX", featureData.energy);
        map.put("entropyX", featureData.entropy);
        map.put("etiqueta", activity.activity.toString());
        map.put("fecha", jsonDate.substring(0, lastTwo) + ":" + jsonDate.substring(lastTwo));
        map.put("idCf", 0d);
        map.put("iqrX", featureData.irq);
        map.put("kurtosisX", featureData.kurt);
        map.put("maxX", featureData.max);
        map.put("meanX", featureData.mean);
        map.put("meanfreqX", featureData.meanf);
        map.put("minX", featureData.min);
        map.put("skewnessX", featureData.skew);
        map.put("stdX", featureData.std);
        map.put("version", String.format("%.3f", activity.modelVersion/1000d));
        map.put("prediccion", activity.feedback ? "S": "N");
        map.put("etiquetaUsuario", activity.feedbackActivity);
        return map;
    }
}
