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

package org.hardroid.common;

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
 *
 * @author agimenez
 */
public class ActivityRecognitionResult implements Parcelable {
    public static final ActivityRecognitionResultCreator CREATOR = new ActivityRecognitionResultCreator();
    public static final String EXTRA_ACTIVITY_RESULT = "org.hardroid.activity.internal.EXTRA_ACTIVITY_RESULT";
    private long time;
    private long elapsedRealtimeMillis;
    private List<HumanActivity> probableActivities;
    private List<Feature> features;
    private int modelVersion = -1;

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

    /**
     * Restricted constructor
     */
    private ActivityRecognitionResult() {
    }

    /**
     * Main constructor
     *
     * @param modelVersion
     *        model version
     * @param probableActivities
     *        list of probable activities
     * @param time
     *        recognition time
     * @param elapsedRealtimeMillis
     *        elapsed time since recognition process started in milliseconds
     */
    public ActivityRecognitionResult(int modelVersion, List<HumanActivity> probableActivities,
                                     long time, long elapsedRealtimeMillis) {
        this.modelVersion = modelVersion;
        this.probableActivities = probableActivities;
        this.time = time;
        this.elapsedRealtimeMillis = elapsedRealtimeMillis;
        Collections.sort(this.probableActivities, ActivityRecognitionResult.descendingOrder);
    }

    /**
     * Alternate constructor with features
     *
     * @param modelVersion
     *        model version
     * @param probableActivities
     *        list of probable activities
     * @param time
     *        recognition time
     * @param elapsedRealtimeMillis
     *        elapsed time since recognition process started in milliseconds
     * @param features
     *        vector data of calculated features
     */
    public ActivityRecognitionResult(int modelVersion,
                                     List<HumanActivity> probableActivities,
                                     long time, long elapsedRealtimeMillis,
                                     List<Feature> features) {
        this(modelVersion, probableActivities, time, elapsedRealtimeMillis);
        this.features = features;
    }

    /**
     * Helper to extract results from an intent
     *
     * @param intent
     *        required intent object
     * @return recognition result
     */
    public static ActivityRecognitionResult extractResult(Intent intent) {
        return (ActivityRecognitionResult) intent.getExtras().get(EXTRA_ACTIVITY_RESULT);
    }

    /**
     * Helper to query if an intent has results
     *
     * @param intent
     *        required intent object
     * @return true if the intent has results in its extra
     */
    public static boolean hasResult(Intent intent) {
        return intent.hasExtra(EXTRA_ACTIVITY_RESULT);
    }

    /**
     * Returns the recognition confidence for an activity type
     *
     * @param activityType
     *        activity type
     * @return confidence in the range of (0, 100)
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
     * Returns the elapsed realtime between the start and
     * completion of the calculation and recognition
     *
     * @return time in milliseconds
     */
    public long getElapsedRealtimeMillis() {
        return this.elapsedRealtimeMillis;
    }

    /**
     * Returns the recognition time
     *
     * @return time in milliseconds
     */
    public long getTime() {
        return this.time;
    }

    /**
     * Returns the activity with highest confidence
     *
     * @return human activity
     */
    public HumanActivity getMostProbableActivity() {
        return probableActivities.get(0);
    }

    /**
     * Returns the list of activities generated during the recognition step
     *
     * @return list of human activity
     */
    public List<HumanActivity> getProbableActivities() {
        return this.probableActivities;
    }

    /**
     * Returns the list of features generated during calculation step
     *
     * @return list of features with raw data
     */
    public List<Feature> getFeatures() {
        return features;
    }

    /**
     * Restricted method to save list of features
     *
     * @param features
     *        list of features generated during calculation step
     */
    protected void setFeatures(List<Feature> features) {
        this.features = features;
    }

    /**
     * Returns the model version that generated this result
     *
     * @return model version
     */
    public int getModelVersion() {
        return modelVersion;
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
        parcel.writeInt(this.modelVersion);
        parcel.writeInt(this.probableActivities.size());
        for (HumanActivity detectedActivity: this.probableActivities) {
            detectedActivity.writeToParcel(parcel, flags);
        }
        parcel.writeLong(this.time);
        parcel.writeLong(this.elapsedRealtimeMillis);
        int featSize = 0;
        if (this.features != null) {
            featSize = this.features.size();
        }
        parcel.writeLong(featSize);
        if (this.features != null) {
            for (Feature data : this.features) {
                data.writeToParcel(parcel, flags);
            }
        }
    }
}
