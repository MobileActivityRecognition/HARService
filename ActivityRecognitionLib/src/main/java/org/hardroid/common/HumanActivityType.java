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

package org.hardroid.common;

/**
 * Constant enum Type used to specify the recognized activity
 */
public enum HumanActivityType {
    /**
     * Unknown activity
     */
    UNKNOWN,
    /**
     * Human is Still
     */
    STILL,
    /**
     * Human is Walking/Standing
     */
    WALKING,
    /**
     * Human is Running
     */
    RUNNING,
    /**
     * Human is In a vehicle
     */
    IN_VEHICLE,
    /**
     * Human is On a bicycle
     */
    ON_BICYCLE,
    /**
     * The device is tilting/shaking
     */
    TILTING

}
