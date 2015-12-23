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

import org.harservice.android.common.HumanActivity;
import org.harsurvey.android.data.DatabaseHelper;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.data.HumanActivityFeed;
import org.harsurvey.android.survey.Config.SyncType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SurveyApplication
 */
public class SurveyApplication extends Application
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String TAG = SurveyApplication.class.getSimpleName();
    private SharedPreferences preferences;
    private DatabaseHelper databaseHelper;
    private String phoneImei;
    private HumanActivityFeed activityFeed;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
        databaseHelper = new DatabaseHelper(this);
        activityFeed = new HumanActivityFeed(databaseHelper);
        Log.i(TAG, "Application started");
        createTest();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }

    public Long getInterval() {
        return preferences.getLong("session_duration", Config.INTERVAL_DEFAULT);
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

    public List<HumanActivityData> getActivitiesUpdates() {
        return activityFeed.getActivityUpdates(HumanActivityData.Status.DRAFT.toString());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        databaseHelper.close();
        Log.i(TAG, "Aplication terminated");
    }

    public void createTest() {
        activityFeed.insertOrIgnore(new HumanActivityData(-1, new Date(),
                HumanActivity.Type.STILL, 0, HumanActivityData.Status.DRAFT, false));
    }
}
