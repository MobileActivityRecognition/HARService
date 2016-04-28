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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.harsurvey.android.util.Constants;

public class DetectorService extends Service {
    private static final String TAG = DetectorService.class.getSimpleName();
    SurveyApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = (SurveyApplication) getApplication();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        Log.i(TAG, "Starting background activity detector");
        app.getConnection().connect();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Stopping background activity detector");
        app.getConnection().release();
        sendBroadcast(new Intent(Constants.SERVICE_CHANGE));
        super.onDestroy();
    }
}
