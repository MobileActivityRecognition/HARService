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

import android.os.SystemClock;
import android.util.Log;

import org.harservice.android.common.ActivityRecognitionResult;
import org.harservice.android.common.HumanActivity;

import java.util.ArrayList;
import java.util.TimerTask;

/**
 * This class is the task that perform human activity recognition in fixed intervals.
 */
public class ActivityRecognitionWorker extends TimerTask {
    public static final String TAG = ActivityRecognitionWorker.class.getSimpleName();
    private ActivityRecognitionService service;
    public static int WINDOW_SIZE = 3000;
    public static int UPDATE_TIME = 10000;

    /**
     * Constructs a new Activity Recognition Worker to resolve Human Activities
     *
     * @param service Parent {@link ActivityRecognitionService} to publish results into
     */
    protected ActivityRecognitionWorker(ActivityRecognitionService service) {
        this.service = service;
    }

    @Override
    public void run() {
        Log.d(TAG, "Collecting activity data");
        ArrayList<HumanActivity> detected = new ArrayList<>();
        for (HumanActivity.Type act : HumanActivity.Type.values()) {
            HumanActivity de = new HumanActivity(act,
                    (int) (Math.random() * 100 + 1));
            detected.add(de);
        }
        this.service.publishResult(new ActivityRecognitionResult(detected,
                System.currentTimeMillis(), SystemClock.elapsedRealtime()));
    }
}
