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

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.hardroid.api.ConnectionApi;
import org.hardroid.client.ActivityRecognitionClient;
import org.hardroid.client.OnClientConnectionListener;
import org.harsurvey.android.survey.R;
import org.harsurvey.android.survey.SurveyApplication;

/**
 * Connection Api responsability
 */
public class ConnectionHelper implements OnClientConnectionListener {
    private static final String TAG = ConnectionHelper.class.getSimpleName();
    private final ActivityRecognitionClient apiClient;
    private final SurveyApplication context;

    public ConnectionHelper(SurveyApplication application, ActivityRecognitionClient apiClient) {
        context = application;
        this.apiClient = apiClient;
        apiClient.addOnConnectionListener(this);
    }

    @Override
    public void onConnect(ConnectionApi connectionApi) {
        Log.i(TAG, "Requesting activities updates");
        Toast.makeText(context, Constants.getStringResource(context, R.string.connected),
                Toast.LENGTH_SHORT).show();
        apiClient.getService().requestActivityUpdates(
                context.getPreference().getInterval(),
                context.getDetectedActivityService());
        context.sendBroadcast(new Intent(Constants.SERVICE_CHANGE));
    }

    @Override
    public void onDisconnect(ConnectionApi connectionApi) {
        Toast.makeText(context, Constants.getStringResource(context, R.string.disconnected),
                Toast.LENGTH_SHORT).show();
    }

    public void release() {
        if (apiClient.isConnected()) {
            Log.i(TAG, "Removing activities updates");
            apiClient.getService().removeActivityUpdates(context.getDetectedActivityService());
            apiClient.disconnect();
            context.sendBroadcast(new Intent(Constants.SERVICE_CHANGE));
        }
    }

    public boolean isClientConnected() {
        return apiClient.isConnected();
    }

    public void connect() {
        if (!apiClient.isConnected()) {
            apiClient.connect();
        }
    }

    public void reconnect() {
        release();
        connect();
    }
}
