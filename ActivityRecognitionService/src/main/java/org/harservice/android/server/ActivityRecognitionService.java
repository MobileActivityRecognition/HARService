/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.harservice.android.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.harservice.android.common.ActivityRecognitionResult;

import java.util.Timer;

/**
 * This class implements the Activity Recognition Service
 */
public class ActivityRecognitionService extends Service {
    public static final String TAG = ActivityRecognitionService.class.getSimpleName();
    public static final String ACCESS_ACTIVITY_RECOGNITION =
            "org.harservice.android.permission.ACTIVITY_RECOGNITION";
    public static final String SEND_ACTIVITY_RECOGNITION =
            "org.harservice.android.permission.ACTIVITY_RECOGNITION_DATA";
    private ActivityRecognitionManagerImpl service;
    private Timer globalTimer;
    private ActivityRecognitionWorker worker;
    private boolean running = false;

    @Override
    public IBinder onBind(Intent intent) {
        return service;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.globalTimer = new Timer();
        this.service = new ActivityRecognitionManagerImpl(this, this.globalTimer);
        this.worker = new ActivityRecognitionWorker(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.running = false;
        this.globalTimer.cancel();
        this.globalTimer.purge();
        this.globalTimer = null;

        Log.v(TAG, "Activity Recognition Service destroyed");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //this.service = null; // Don't do this
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (!this.running) {
            Log.v(TAG, "Activity Recognition Service started");
            this.running = true;
            this.globalTimer.schedule(this.worker, 0, ActivityRecognitionWorker.UPDATE_TIME);
        }
        return START_STICKY;
    }

    /**
     * Method to update activity recognition results.
     *
     * @param result {@link ActivityRecognitionResult} to be published
     */
    public void publishResult(ActivityRecognitionResult result) {
        this.service.setResult(result);
    }
}
