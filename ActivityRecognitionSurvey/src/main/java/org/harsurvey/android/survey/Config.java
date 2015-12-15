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

package org.harsurvey.android.survey;


/**
 * Configuration Parameters
 */
public class Config {
    /**
     * Set default clock units
     */
    public static final int SECOND = 1000;
    public static final int MINUTE = 60*SECOND;
    public static final int HOUR = 60*MINUTE;

    /**
     * Set default INTERVAL TIME
     */
    public static final int INTERVAL_DEFAULT = 1*MINUTE;

    /**
     * Declaring KEYS for saving data
     */
    public static final String PREFERENCE_KEY = "SURVEY_PREFERENCES";
    public static final String IMEI_KEY = "IMEI";

    public static final String DATABASE_NAME = "humanactivity.db";
    public static final int DATABASE_VERSION = 1;
}
