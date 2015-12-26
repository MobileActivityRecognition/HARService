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

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.harservice.android.common.ActivityRecognitionResult;
import org.harservice.android.common.HumanActivity;

import java.util.List;

/**
 * Detect Activities and send broadcast
 */
public class DetectedActivitiesService extends IntentService {
    public static final String TAG = DetectedActivitiesService.class.getSimpleName();
    private SurveyApplication app;

    public DetectedActivitiesService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent localIntent = new Intent(Config.DETECTED_ACTIVITY_BROADCAST);

        HumanActivity activity = result.getMostProbableActivity();

        List<HumanActivity> detectedActivities = result.getProbableActivities();

        // Log each activity.
        Log.i(TAG, "Activities detected");
        for (HumanActivity da: detectedActivities) {
            Log.d(TAG, da.getType().toString() + " " + da.getConfidence() + "%");
        }

        localIntent.putExtra(Config.DETECTED_ACTIVITY_EXTRA, activity);
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    public String getActivityString(Context context, HumanActivity.Type detectedActivityType) {
        Resources resources = context.getResources();
        switch(detectedActivityType) {
            case IN_VEHICLE:
                return resources.getString(R.string.in_vehicle);
            case ON_BICYCLE:
                return resources.getString(R.string.on_bicycle);
            case RUNNING:
                return resources.getString(R.string.running);
            case STILL:
                return resources.getString(R.string.still);
            case UNKNOWN:
                return resources.getString(R.string.unknown);
            case WALKING:
                return resources.getString(R.string.walking);
            default:
                return resources.getString(R.string.unidentifiable_activity,
                        detectedActivityType.toString());
        }
    }

}
