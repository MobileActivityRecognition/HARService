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
 * The main entry point to interact with activity recognition
 */
public interface ActivityRecognitionApi {
    /**
     * @param detectionIntervalMillis
     * @param callbackIntent
     */
    public void requestActivityUpdates(long detectionIntervalMillis,
                                       PendingIntent callbackIntent);

    /**
     * @param detectionIntervalMillis
     * @param listener
     */
    public void requestActivityUpdates(long detectionIntervalMillis,
                                       ActivityRecognitionListener listener);

    /**
     * @param detectionIntervalMillis
     * @param listener
     * @param looper
     */
    public void requestActivityUpdates(long detectionIntervalMillis,
                                       ActivityRecognitionListener listener, Looper looper);

    /**
     * @param callbackIntent
     */
    public void requestSingleUpdate(PendingIntent callbackIntent);

    /**
     * @param callbackIntent
     */
    public void removeActivityUpdates(PendingIntent callbackIntent);

    /**
     * @param listener
     */
    public void removeActivityUpdates(ActivityRecognitionListener listener);
}
