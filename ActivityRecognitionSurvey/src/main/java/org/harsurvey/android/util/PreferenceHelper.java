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

import android.content.SharedPreferences;
import android.util.Log;

import org.harsurvey.android.survey.R;
import org.harsurvey.android.survey.SurveyApplication;

import java.util.Calendar;
import java.util.Date;

/**
 * Preference and settings
 */
public class PreferenceHelper implements SharedPreferences.OnSharedPreferenceChangeListener {
    private final String TAG = PreferenceHelper.class.getSimpleName();
    private final SharedPreferences preferences;
    private final SurveyApplication context;

    public PreferenceHelper(SurveyApplication application, SharedPreferences preferences) {
        context = application;
        this.preferences = preferences;
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Log.i(TAG, "Preferencias cambiadas " + s);
        if (s.equals(Constants.getStringResource(context, R.string.pref_key_duration)) &&
                context.hasValidAccount()) {
            context.getConnection().reconnect();
        }
    }

    public long getInterval() {
        String value = preferences.getString(Constants.getStringResource(context,
                R.string.pref_key_duration), null);
        if (value == null) {
            return Constants.INTERVAL_DEFAULT;
        }
        else {
            long intValue = Long.valueOf(value);
            if (intValue > 0) {
                return intValue * Constants.MINUTE;
            }
            else {
                return Constants.MINUTE / 2;
            }
        }
    }

    public String getSyncMethod() {
        return preferences.getString(Constants.getStringResource(context,
                        R.string.pref_key_sync),
                Constants.getStringResource(context, R.string.pref_default_sync_method));
    }

    public boolean isSyncEnabled() {
        return preferences.getBoolean(Constants.getStringResource(context,
                R.string.pref_key_conn), true);
    }


    public String getIMEI() {
        return preferences.getString(Constants.getStringResource(context,
                R.string.pref_key_imei), Constants.NONE);
    }

    public String getName() {
        return preferences.getString(Constants.getStringResource(context,
                R.string.pref_key_name), Constants.NONE);
    }

    public String getAge() {
        int result = preferences.getInt(Constants.getStringResource(context, R.string.pref_key_age),
                -1);
        if (result > NumberPickerPreference.MIN_VALUE &&
                result < NumberPickerPreference.MAX_VALUE) {
            return String.valueOf(result);
        }
        else {
            return Constants.NONE;
        }
    }

    public String getPhoneName() {
        return preferences.getString(Constants.getStringResource(context,
                R.string.pref_key_model), Constants.NONE);
    }

    public String getSensorName() {
        return preferences.getString(Constants.getStringResource(context,
                R.string.pref_key_sensor), Constants.NONE);
    }

    public void disableService() {
        preferences.edit().putBoolean(Constants.getStringResource(context, R.string.pref_service_active), false).apply();
    }

    public void enableService() {
        preferences.edit().putBoolean(Constants.getStringResource(context, R.string.pref_service_active), true).apply();
    }

    public boolean getServiceStatus() {
        return preferences.getBoolean(Constants.getStringResource(context, R.string.pref_service_active), false);
    }

    public Long getLastUpdateTime() {
        Date now = Calendar.getInstance().getTime();
        long delta = now.getTime() - 6 * Constants.HOUR;
        long saved = preferences.getLong(Constants.getStringResource(context, R.string.pref_last_update),
                delta);
        return Math.max(saved, delta);
    }

    public int getNotificationOption() {
        return preferences.getInt(Constants.getStringResource(context, R.string.pref_notification_option),
                -1);
    }

    public long getLastNotificationTime() {
        long now = Constants.getCurrentTime();
        return preferences.getLong(Constants.getStringResource(context, R.string.pref_not_last_time),
                now);
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        preferences.edit().putLong(Constants.getStringResource(context, R.string.pref_last_update),
                lastUpdateTime).apply();
    }

    public void setNotificationOption(int notificationOption) {
        preferences.edit().putInt(Constants.getStringResource(context, R.string.pref_notification_option),
                notificationOption).apply();
    }

    public void setNotificationLastTime(long time) {
        preferences.edit().putLong(Constants.getStringResource(context, R.string.pref_not_last_time),
                time).apply();
    }
}

