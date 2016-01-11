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

package org.harservice.android.server;

import android.util.Log;

import org.harservice.android.common.IActivityRecognitionResponseListener;

import java.util.Comparator;

/**
 * This class holds an Activity Recognition client subscription
 */
public class ActivityRecognitionSubscription {

    public static final String TAG = ActivityRecognitionSubscription.class.getSimpleName();
    private String appId;
    private long detectionInterval;
    private long lastUpdateTime;
    private IActivityRecognitionResponseListener listener;

    public static class DetectionTimeComparator implements Comparator<ActivityRecognitionSubscription> {

        @Override
        public int compare(ActivityRecognitionSubscription lhs,
                           ActivityRecognitionSubscription rhs) {
            return Long.valueOf(rhs.getDetectionInterval()).compareTo(rhs.getDetectionInterval());
        }

    }


    /**
     * Private constructor
     *
     * Don't create objects directly
     */
    private ActivityRecognitionSubscription() {
    }

    /**
     * Creates an client subscrioption
     * @param appId client application identification
     * @param detectionIntervalMillis detection time in milliseconds
     * @param listener client callback
     */
    protected ActivityRecognitionSubscription(String appId, long detectionIntervalMillis,
                                              IActivityRecognitionResponseListener listener) {
        this.listener = listener;
        Log.d(TAG, "Creating new subscriber thread");
        this.appId = appId;
        this.detectionInterval = detectionIntervalMillis;
    }

    /**
     * Returns the last update time
     * @return last update time
     */
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * Sets the last update time
     * @param lastUpdateTime new last update time
     */
    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    /**
     * Returns the detection time
     * @return detection time interval in milliseconds
     */
    public long getDetectionInterval() {
        return detectionInterval;
    }

    /**
     * Returns the clients callback
     * @return client callback
     */
    public IActivityRecognitionResponseListener getListener() {
        return listener;
    }
}
