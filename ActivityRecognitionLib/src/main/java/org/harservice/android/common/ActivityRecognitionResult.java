/*
 * HARService: Activity Recognition Service
 * Copyright (C) 2015 agimenez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.harservice.android.common;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Results of an activity recognition prediction.
 *
 * Contains a list of predicted activities that the user might be doing during a period of time.
 * The Activities are sorted by most probable activities
 */
public class ActivityRecognitionResult implements Parcelable {
    public static final ActivityRecognitionResultCreator CREATOR = new ActivityRecognitionResultCreator();
    public static final String EXTRA_ACTIVITY_RESULT = "org.harservice.android.activity.internal.EXTRA_ACTIVITY_RESULT";
    private long time;
    private long elapsedRealtimeMillis;
    private List<HumanActivity> probableActivities;

    private static Comparator<HumanActivity> descendingOrder = new Comparator<HumanActivity>() {
        @Override
        public int compare(HumanActivity lhs, HumanActivity rhs) {
            if (lhs.getConfidence() > rhs.getConfidence()) {
                return -1;
            }
            else if (lhs.getConfidence() < rhs.getConfidence()) {
                return 1;
            }
            else {
                return 0;
            }
        }
    };

    private ActivityRecognitionResult() {
    }

    /**
     * @param probableActivities
     * @param time
     * @param elapsedRealtimeMillis
     */
    public ActivityRecognitionResult(List<HumanActivity> probableActivities, long time,
                                     long elapsedRealtimeMillis) {
        this.probableActivities = probableActivities;
        this.time = time;
        this.elapsedRealtimeMillis = elapsedRealtimeMillis;
        Collections.sort(this.probableActivities, ActivityRecognitionResult.descendingOrder);
    }

    /**
     * @param mostProbableActivity
     * @param time
     * @param elapsedRealtimeMillis
     */
    public ActivityRecognitionResult(HumanActivity mostProbableActivity, long time, long elapsedRealtimeMillis) {
    }

    /**
     * @param intent
     * @return
     */
    public static ActivityRecognitionResult extractResult(Intent intent) {
        return (ActivityRecognitionResult) intent.getExtras().get(EXTRA_ACTIVITY_RESULT);
    }

    /**
     * @param intent
     * @return
     */
    public static boolean hasResult(Intent intent) {
        return intent.hasExtra(EXTRA_ACTIVITY_RESULT);
    }

    /**
     * @param activityType
     * @return
     */
    public int getActivityConfidence(HumanActivity.Type activityType) {
        int confidence = -1;
        for (HumanActivity detectedActivity: this.probableActivities) {
            if (detectedActivity.getType() == activityType) {
                confidence = detectedActivity.getConfidence();
                break;
            }
        }
        return confidence;
    }

    /**
     * @return
     */
    public long getElapsedRealtimeMillis() {
        return this.elapsedRealtimeMillis;
    }

    /**
     * @return
     */
    public long getTime() {
        return this.time;
    }

    /**
     * @return
     */
    public HumanActivity getMostProbableActivity() {
        return probableActivities.get(0);
    }

    public List<HumanActivity> getProbableActivities() {
        return this.probableActivities;
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.probableActivities.size());
        for (HumanActivity detectedActivity: this.probableActivities) {
            detectedActivity.writeToParcel(parcel, flags);
        }
        parcel.writeLong(this.time);
        parcel.writeLong(this.elapsedRealtimeMillis);
    }
}
