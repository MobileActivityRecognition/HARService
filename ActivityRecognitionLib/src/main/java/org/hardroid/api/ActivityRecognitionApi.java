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

package org.hardroid.api;

import android.app.PendingIntent;
import android.os.Looper;

import org.hardroid.client.ActivityRecognitionListener;

/**
 * This interface define the service contract to subscribe to recognition of activities
 *
 * @author agimenez
 */
public interface ActivityRecognitionApi {
    /**
     * Request a periodic update from the service with the specified parameters
     *
     * @param detectionIntervalMillis
     *        interval between updates
     * @param callbackIntent
     *        required object to callback when the service completes
     */
    void requestActivityUpdates(long detectionIntervalMillis, PendingIntent callbackIntent);

    /**
     * Request a periodic update from the service with the specified parameters
     *
     * @param detectionIntervalMillis
     *        interval between updates
     * @param listener
     *        required object that listens when then service completes
     */
    void requestActivityUpdates(long detectionIntervalMillis, ActivityRecognitionListener listener);

    /**
     * Request a periodic update from the service with the specified parameters
     *
     * @param detectionIntervalMillis
     *        interval between updates
     * @param listener
     *        required object that listens when the service completes
     * @param looper
     *        main process looper on to execute
     */
    void requestActivityUpdates(long detectionIntervalMillis,
                                       ActivityRecognitionListener listener, Looper looper);

    /**
     * Request a single update
     *
     * @param callbackIntent
     *        required object to callback when the service completes
     */
    void requestSingleUpdate(PendingIntent callbackIntent);

    /**
     * Remove a subscription associated with this callback
     *
     * @param callbackIntent
     *        required object to callback when the service completes
     */
    void removeActivityUpdates(PendingIntent callbackIntent);

    /**
     * Remove a subscription associated with this listener
     *
     * @param listener
     *        required object that listents when the service completes
     */
    void removeActivityUpdates(ActivityRecognitionListener listener);
}
