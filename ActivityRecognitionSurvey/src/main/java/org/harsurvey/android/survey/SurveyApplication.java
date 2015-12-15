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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.harsurvey.android.data.DatabaseHelper;

/**
 * SurveyApplication
 */
public class SurveyApplication extends Application
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    public static final String TAG = SurveyApplication.class.getSimpleName();
    private SharedPreferences preferences;
    private DatabaseHelper databaseHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        this.preferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.preferences.registerOnSharedPreferenceChangeListener(this);
        this.databaseHelper = new DatabaseHelper(this);
        Log.i(TAG, "Application started");
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }
}
