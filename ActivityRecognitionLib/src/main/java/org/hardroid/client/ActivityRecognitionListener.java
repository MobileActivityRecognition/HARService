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

package org.hardroid.client;

import org.hardroid.common.ActivityRecognitionResult;

/**
 * This interface is a contract to receive notifications
 * from {@link ActivityRecognitionManager} when a new result is available
 *
 * @author agimenez
 */
public interface ActivityRecognitionListener {
    /**
     * New Activity Recognition result event subscription
     *
     * @param result
     *        activity recognition result provided by the service
     */
    void onActivityChanged(ActivityRecognitionResult result);
}
