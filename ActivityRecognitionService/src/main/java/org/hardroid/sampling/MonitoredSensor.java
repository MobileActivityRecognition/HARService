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

package org.hardroid.sampling;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;
import android.util.Log;

import org.hardroid.features.SignalProcessing;

import static org.hardroid.utils.Constants.FILTER_SIZE;
import static org.hardroid.utils.Constants.SAMPLE_SIZE;
import static org.hardroid.utils.Constants.X;
import static org.hardroid.utils.Constants.Y;
import static org.hardroid.utils.Constants.Z;

/**
 * Implements an independent sensor collector service that broadcast sensor results every X seconds
 * depending on the client subscription interval
 */
public class MonitoredSensor {
    public static final String TAG = MonitoredSensor.class.getSimpleName();

    private Sensor sensor;
    private boolean listening = false;
    private onSensorEventListener sensorListener;
    private Context context;
    private SensorDataFinishListener listener;

    public MonitoredSensor(Context context, SensorDataFinishListener listener) {
        this.context = context;
        this.listener = listener;
        sensor = getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public int getType() {
        return sensor.getType();
    }

    public String getName() {
        return sensor.getName();
    }

    public boolean isListening() {
        return listening;
    }

    private SensorManager getSensorManager() {
        return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * Posible DELAY values
     * - SENSOR_DELAY_NORMAL : 200.000 microsec
     * - SENSOR_DELAY_GAME : 20.000 microsec  <----- 60 Hz
     * - SENSOR_DELAY_UI : 60.000 microsec
     * - SENSOR_DELAY_FASTEST : 2.500 (NEXUS 5X),
     *                          5.000 (Huawei M9),
     *                          8.333 (Oneplus One) microsec
     * If SAMPLE_SIZE = 512 and SLICE_SIZE = SAMPLE_SIZE / 4 = 128
     * then
     *    SLICE_SIZE * SENSOR_DELAY_GAME = 2.56 seconds
     */
    public void startListening() {
        sensorListener = new onSensorEventListener();
        listening = true;
        getSensorManager().registerListener(sensorListener, this.sensor,
                SensorManager.SENSOR_DELAY_GAME);
        Log.d(TAG, "Sensor Event Listener started at " + sensorListener.startTime);
    }

    public void stopListening() {
        getSensorManager().unregisterListener(sensorListener);
        long currentTime = SystemClock.elapsedRealtime();
        Log.d(TAG, "Sensor Event Listener stopped at " + currentTime + " elapsed " + (currentTime - sensorListener.startTime));
        sensorListener = null;
    }

    private class onSensorEventListener implements SensorEventListener {
        private long startTime;
        private float[][] sensorData = new float[SAMPLE_SIZE][3];
        private int idxArray = 0;
        private int idxCapture = 0;
        private float[][] sampleWindow = new float[FILTER_SIZE][3];

        public onSensorEventListener() {
            startTime = SystemClock.elapsedRealtime();
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            long now = SystemClock.elapsedRealtime();

            float[] values = event.values;
            idxCapture += 1;
            if (idxCapture < SAMPLE_SIZE + FILTER_SIZE) {
                SignalProcessing.roll(sampleWindow, X, values[X]);
                SignalProcessing.roll(sampleWindow, Y, values[Y]);
                SignalProcessing.roll(sampleWindow, Z, values[Z]);

                if (idxCapture >= FILTER_SIZE) {
                    sensorData[idxArray] = SignalProcessing.average(sampleWindow);
                    idxArray += 1;
                }
            }
            else {
                stopListening();
                listener.onSensorData(startTime, now, sensorData);
                listening = false;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    }
}
