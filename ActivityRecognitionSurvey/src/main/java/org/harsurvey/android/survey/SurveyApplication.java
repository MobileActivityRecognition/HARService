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
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.harservice.android.api.ConnectionApi;
import org.harservice.android.client.ActivityRecognitionClient;
import org.harservice.android.client.OnClientConnectionListener;
import org.harservice.android.common.HumanActivity;
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
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        apiClient = new ActivityRecognitionClient(this, this);
        notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(activityReceiver,
                new IntentFilter(Constants.DETECTED_ACTIVITY_BROADCAST));
        Log.i(TAG, "Application started");
    }

    @Override
    public void onTerminate() {
        if (apiClient.isConnected()) {
            apiClient.disconnect();
        }
        super.onTerminate();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(activityReceiver);
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

    private BroadcastReceiver activityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "Nuevos datos disponibles");
            HumanActivity activity = intent.getParcelableExtra(Constants.DETECTED_ACTIVITY_EXTRA);
            sendNotification(activity);
        }
    };

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    public long getInterval() {
        String value  = preferences.getString("session_duration", null);
        if (value == null) { // TODO: CAMBIAR PARA QUE AGARRE LA CONFIGURACION
            return Constants.INTERVAL_DEFAULT;
        }
        else {
            return Long.valueOf(value)* Constants.MINUTE;
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
        Log.i(TAG, "Removing activities updates");
        apiClient.getService().removeActivityUpdates(getDetectedActivityService());
    }

    public boolean isClientConnected() {
        return apiClient.isConnected();
    }

    private void sendNotification(HumanActivity activity) {
        Intent activityIntent = new Intent(this, FeedActivity.class);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Resources resources = getResources();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(Constants.getBitmapIcon(getApplicationContext(),
                        R.drawable.ic_notification_unkown))
                .setContentTitle(resources.getString(R.string.notification_title))
                .setContentText(resources.getString(R.string.notification_text))
                .setSubText(resources.getString(R.string.notification_description))
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        notificationManager.notify(1, builder.build());
    }

}
