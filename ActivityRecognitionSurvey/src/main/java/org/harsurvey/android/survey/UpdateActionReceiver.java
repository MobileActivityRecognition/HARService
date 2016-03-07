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

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.util.Log;

import org.harsurvey.android.data.FeatureData;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.util.Constants;

import java.util.List;

public class UpdateActionReceiver extends BroadcastReceiver {

    private static final String TAG = UpdateActionReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        SurveyApplication app = ((SurveyApplication) context.getApplicationContext());
        String action = intent.getAction();
        Log.i(TAG, "Request database syncronization");
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION) ||
                action.equals(Constants.REQUEST_SYNCRONIZATION)) {
            if (app.getPreference().isSyncEnabled()
                    && app.isOnline()) {
                Log.d(TAG, "Connection available sync information");
                Cursor cursor = app.getContentResolver().query(HumanActivityData.CONTENT_URI,
                        HumanActivityData.Contract.ALL_COLUMNS,
                        "status = 'PENDING'",
                        null, null);
                List<HumanActivityData> dataList = HumanActivityData.CREATOR.createFromCursor(cursor);
                for (HumanActivityData activity : dataList ) {
                    Cursor otherCursor = app.getContentResolver().query(FeatureData.CONTENT_URI,
                            FeatureData.Contract.ALL_COLUMNS,
                            "activity_id = ?", new String[]{String.valueOf(activity.getId())}, null);
                    List<FeatureData> featureDataList = FeatureData.CREATOR.createFromCursor(otherCursor);
                    app.getRestService().saveFeatureData(activity, featureDataList);
                }
            }

        }
    }
}
