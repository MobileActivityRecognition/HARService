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

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.harsurvey.android.survey.R;
import org.harsurvey.android.survey.SurveyApplication;

/**
 * Gathers related phone information
 */
public class PhoneInfoHelper {

    private final SurveyApplication context;

    public PhoneInfoHelper(SurveyApplication application, SharedPreferences preferences) {
        context = application;;
        String key = Constants.getStringResource(context, R.string.pref_key_imei);
        if (preferences.getString(key, null) == null) {
            preferences.edit().putString(key,
                    Constants.getDeviceId(context)).apply();
        }
        key = Constants.getStringResource(context, R.string.pref_key_model);
        if (preferences.getString(key, null) == null) {
            preferences.edit().putString(key,
                    Constants.getDeviceName()).apply();
        }

        key = Constants.getStringResource(context, R.string.pref_key_name);
        if (preferences.getString(key, null) == null) {
            preferences.edit().putString(key,
                    Constants.getDeviceOwner()).apply();
        }

        key = Constants.getStringResource(context, R.string.pref_key_sensor);
        if (preferences.getString(key, null) == null) {
            SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            String sensorName = String.format("%s %s (%d)", sensor.getName(),
                    sensor.getVendor(), sensor.getVersion());
            preferences.edit().putString(key,
                    sensorName).apply();
        }
    }


}


