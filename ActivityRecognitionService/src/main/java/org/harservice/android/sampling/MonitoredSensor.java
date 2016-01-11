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

package org.harservice.android.sampling;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.SystemClock;

import java.util.ArrayList;

/**
 * Implements an independent sensor collector service that broadcast sensor results every X seconds
 * depending on the client subscription interval
 */
public class MonitoredSensor {
    public static final String TAG = MonitoredSensor.class.getSimpleName();

    private Sensor sensor;
    private boolean listening = false;
    private long sampleTime;
    private onSensorEventListener sensorListener;
    private Context context;
    private SensorDataFinishListener listener;

    public MonitoredSensor(Context context, SensorDataFinishListener listener, long sampleTime) {
        this.context = context;
        this.listener = listener;
        this.sampleTime = sampleTime;
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

    public SensorManager getSensorManager() {
        return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    public void startListening() {
        this.sensorListener = new onSensorEventListener();
        listening = true;
        getSensorManager().registerListener(sensorListener, this.sensor,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stopListening() {
        getSensorManager().unregisterListener(sensorListener);
        sensorListener = null;
        listening = false;
    }

    private class onSensorEventListener implements SensorEventListener {
        private long starTime;
        private ArrayList<float[]> sensorData = new ArrayList<>();

        public onSensorEventListener() {
            starTime = SystemClock.elapsedRealtime();
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            long now = SystemClock.elapsedRealtime();
            long deltaMs = 0;

            float[] values = event.values;
            deltaMs = now - event.timestamp;
            if (now - starTime > MonitoredSensor.this.sampleTime) {
                listener.onSensorData(sensorData);
                stopListening();
            }
            else {
                sensorData.add(values);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    }
}
