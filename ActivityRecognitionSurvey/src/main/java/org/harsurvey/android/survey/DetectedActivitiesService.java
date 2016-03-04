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
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import org.hardroid.common.ActivityRecognitionResult;
import org.hardroid.common.Feature;
import org.hardroid.common.HumanActivity;
import org.harsurvey.android.data.FeatureData;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.util.Constants;

import java.util.Date;

/**
 * Detect Activities and send broadcast
 */
public class DetectedActivitiesService extends IntentService {
    public static final String TAG = DetectedActivitiesService.class.getSimpleName();

    public DetectedActivitiesService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
        Intent localIntent = new Intent(Constants.DETECTED_ACTIVITY_BROADCAST);

        HumanActivity activity = result.getMostProbableActivity();

        Date time = new Date();
        HumanActivityData activityData = new HumanActivityData(time,
                activity.getType(), activity.getConfidence(),
                HumanActivityData.Status.DRAFT, false);
        Uri uri = getContentResolver().insert(HumanActivityData.CONTENT_URI,
                activityData.getValues());
        if (uri != null) {
            Log.d(TAG,  String.format("Saved Activity Result %s: %s", activity.getType(),
                    activity.getConfidence()));
            long lastId = ContentUris.parseId(uri);

            for (Feature feature : result.getFeatures()) {
                if (activity.getType() != feature.getActivityLabel()) {
                    continue;
                }
                double[] data = feature.getData();
                FeatureData featureData = new FeatureData(lastId, data[0], data[1], data[2], data[3],
                        data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11],
                        data[12], data[13]);
                Uri alternateUri = getContentResolver().insert(FeatureData.CONTENT_URI,
                        featureData.getValues());
                if (alternateUri != null) {
                    Log.d(TAG,  String.format("Saved Feature Result %s: %s data",
                            feature.getActivityLabel(),
                            feature.getFeatureSize()));
                }
            }
        }

        localIntent.putExtra(Constants.DETECTED_ACTIVITY_EXTRA, activity);
        sendBroadcast(localIntent);

    }

}
