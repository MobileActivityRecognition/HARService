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

/**
 * Sampling default values
 */
public class Constants {
    /**
     * Set default clock units
     */
    public static final int SECOND = 1000;
    public static final int MINUTE = 60*SECOND;
    public static final int HOUR = 60*MINUTE;

    /**
     * Set default INTERVAL TIME
     */
    public static final long INTERVAL_DEFAULT = 10*SECOND;
    public static final long CALCULATION_DEFAULT = 5*SECOND;
    public static final long SAMPLETIME_DEFAULT = (long) 2.5*SECOND;

    public static final String ACCESS_ACTIVITY_RECOGNITION =
            "org.harservice.android.permission.ACTIVITY_RECOGNITION";
    public static final String SEND_ACTIVITY_RECOGNITION =
            "org.harservice.android.permission.ACTIVITY_RECOGNITION_DATA";
}
