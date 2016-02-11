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
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.harservice.android.api.ConnectionApi;
import org.harservice.android.client.ActivityRecognitionClient;
import org.harservice.android.client.OnClientConnectionListener;
import org.harsurvey.android.survey.Constants.SyncType;

/**
 * SurveyApplication
 */
public class SurveyApplication extends Application
        implements SharedPreferences.OnSharedPreferenceChangeListener, OnClientConnectionListener {

    public static final String TAG = SurveyApplication.class.getSimpleName();
    private SharedPreferences preferences;
    private String phoneImei;
    private ActivityRecognitionClient apiClient;
    private PendingIntent detectedActivityService;
    private boolean onTop = false;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        apiClient = new ActivityRecognitionClient(this);
        apiClient.addOnConnectionListener(this);
        Log.i(TAG, "Application started");
    }

    public void cleanUp() {
        if (apiClient.isConnected()) {
            Log.i(TAG, "Removing activities updates");
            apiClient.getService().removeActivityUpdates(getDetectedActivityService());
            apiClient.disconnect();
        }
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    public long getInterval() {
        String value = preferences.getString("session_duration", null);
        if (value == null || true) {
            return Constants.INTERVAL_DEFAULT / 2;
        }
        else {
            return Long.valueOf(value) * Constants.MINUTE;
        }
    }

    public SyncType getSyncMethod() {
        return SyncType.valueOf(
                preferences.getString("sync_data_method",
                        getString(R.string.pref_default_sync_method))
        );
    }

    public String getPhoneImei() {
        return phoneImei;
    }


    public void setPhoneImei(String phoneImei) {
        this.phoneImei = phoneImei;
    }

    public void connect() {
        if (!apiClient.isConnected()) {
            apiClient.connect();
        }
    }

    @Override
    public void onConnect(ConnectionApi connectionApi) {
        Log.i(TAG, "Requesting activities updates");
        apiClient.getService().requestActivityUpdates(
                getInterval(),
                getDetectedActivityService());
    }

    @Override
    public void onDisconnect(ConnectionApi connectionApi) {
    }

    public boolean isClientConnected() {
        return apiClient.isConnected();
    }

    public boolean isOnTop() {
        return onTop;
    }

    public void setOnTop(boolean onTop) {
        this.onTop = onTop;
    }
}
