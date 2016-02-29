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

package org.harsurvey.android.survey;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import org.hardroid.client.ActivityRecognitionClient;
import org.harsurvey.android.util.ConnectionHelper;
import org.harsurvey.android.util.PhoneInfoHelper;
import org.harsurvey.android.util.PreferenceHelper;
import org.harsurvey.android.util.RestServiceHelper;

/**
 * SurveyApplication
 */
public class SurveyApplication extends Application {

    public static final String TAG = SurveyApplication.class.getSimpleName();
    private PendingIntent detectedActivityService;
    private boolean onTop = false;
    private PhoneInfoHelper phoneInfo;
    private ConnectionHelper connection;
    private PreferenceHelper preference;
    private RestServiceHelper restService;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preference = new PreferenceHelper(this, preferences);
        phoneInfo = new PhoneInfoHelper(this, preferences);
        connection = new ConnectionHelper(this, new ActivityRecognitionClient(this));
        restService = new RestServiceHelper(this);
        Log.i(TAG, "Application started");
    }

    public void cleanUp() {
        connection.release();
        Log.i(TAG, "Aplication terminated");
    }

    public PendingIntent getDetectedActivityService() {
        if (detectedActivityService != null) {
            return detectedActivityService;
        }
        Intent intent = new Intent(this, DetectedActivitiesService.class);

        detectedActivityService = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return detectedActivityService;
    }

    public boolean isOnTop() {
        return onTop;
    }

    public void setOnTop(boolean onTop) {
        this.onTop = onTop;
    }

    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        String method = preference.getSyncMethod();
        if (networkInfo != null) {
            boolean myNetwork = method.equals("ALL") ||
                    networkInfo.getTypeName().equals(method);
            return networkInfo.isConnected() && myNetwork;
        }
        else {
            return false;
        }
    }

    public PhoneInfoHelper getPhoneInfo() {
        return phoneInfo;
    }

    public PreferenceHelper getPreference() {
        return preference;
    }

    public ConnectionHelper getConnection() {
        return connection;
    }

    public RestServiceHelper getRestService() {
        return restService;
    }
}
