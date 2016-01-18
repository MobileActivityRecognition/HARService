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
import org.harservice.android.features.FeatureProcessing;
import org.harservice.android.model.ActivityClassifier;
import org.harservice.android.model.DecisionTreeClassifier;
import org.harservice.android.model.RandomClassifier;
import org.harservice.android.sampling.MonitoredSensor;
import org.harservice.android.sampling.SensorDataFinishListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.harservice.android.server.Constants.VariableType;
import static org.harservice.android.server.Constants.SAMPLE_SIZE;
import static org.harservice.android.server.Constants.SLICE_SIZE;

/**
 * This class is the task that perform human activity recognition in fixed intervals.
 */
public class ActivityRecognitionWorker implements SensorDataFinishListener {
    public static final String TAG = ActivityRecognitionWorker.class.getSimpleName();
    private ActivityRecognitionService context;
    private long intervalTime = Integer.MAX_VALUE;
    private long newIntervalTime = -1;
    private boolean newTime = false;
    private final MonitoredSensor monitoredSensor;
    private final ActivityClassifier activityClassifier;
    private Timer scheduler;
    private TimerWorker timerWorker;


    /**
     * Constructs a new Activity Recognition Worker to resolve Human Activities
     *
     * @param context Parent {@link ActivityRecognitionService} to publish results into
     */
    protected ActivityRecognitionWorker(ActivityRecognitionService context) {
        this.context = context;

        scheduler = new Timer();
        monitoredSensor = new MonitoredSensor(context, this);
        activityClassifier = new DecisionTreeClassifier();
    }

    public boolean isValidTime(long timer) {
        return timer < this.intervalTime && timer >= Constants.INTERVAL_DEFAULT;
    }

    public void starTimer(long timer) {
        if (isValidTime(timer) && !monitoredSensor.isListening()) {
            Log.i(TAG, "Setting new time interval " + timer);
            stopTimer();
            intervalTime = timer;
            long firstTime = timer - Constants.CALCULATION_DEFAULT;
            timerWorker = new TimerWorker();
            scheduler.schedule(timerWorker, firstTime, intervalTime);
        }
        else if (isValidTime(timer) && monitoredSensor.isListening()) {
            Log.i(TAG, "Pending new time interval " + timer);
            newTime = true;
            newIntervalTime = timer;
        }
    }

    public void stopTimer() {
        intervalTime = Integer.MAX_VALUE;
        if (timerWorker != null) {
            timerWorker.cancel();
        }
    }

    public void cancel() {
        this.scheduler.purge();
        this.scheduler.cancel();
    }

    @Override
    public void onSensorData(long startTime, long now, float[][] sensorData) {
        Log.d(TAG, "Calculating activity " + sensorData.length);
        double[] results = new double[Constants.ACTIVITY_COUNT];
        int steps = SLICE_SIZE / 2;   // STEPS = 64, SLICE_SIZE = 128, SAMPLE_SIZE = 512
        double slices = SAMPLE_SIZE / steps  - 1; // SLICE = 7 es 100%
        Log.d(TAG, "Number of slices " + slices);
        for (int i = 0; i + SLICE_SIZE <= SAMPLE_SIZE ; i += steps) {
            double[] featureData = FeatureProcessing.calculateSample(i,
                    i + SLICE_SIZE,
                    SLICE_SIZE,
                    sensorData, VariableType.MAG);
            HumanActivity.Type result = activityClassifier.classify(featureData);
            results[result.ordinal()] += 1.0/slices;
            Log.d(TAG, String.format("Activity detected %s (%.2f)", result.toString(),
                    results[result.ordinal()]*100));
        }
        ArrayList<HumanActivity> detected = new ArrayList<>();
        for (HumanActivity.Type act : HumanActivity.Type.values()) {
            int confidence = (int) Math.round(results[act.ordinal()]*100);
            HumanActivity de = new HumanActivity(act, confidence);
            detected.add(de);
        }
        this.context.publishResult(new ActivityRecognitionResult(detected,
                startTime, now - startTime));
    }

    private class TimerWorker extends TimerTask {
        @Override
        public void run() {
            synchronized (ActivityRecognitionWorker.this.monitoredSensor) {
                MonitoredSensor sensorTask = ActivityRecognitionWorker.this.monitoredSensor;
                if (!sensorTask.isListening()) {
                    Log.d(TAG + "-TimerWorker", "Collecting sensor data");
                    sensorTask.startListening();
                    if (newTime) {
                        newTime = false;
                        starTimer(newIntervalTime);
                        newIntervalTime = -1;
                    }
                }
            }
        }
    };
}
