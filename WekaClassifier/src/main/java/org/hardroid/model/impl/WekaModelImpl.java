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

package org.hardroid.model.impl;

import org.hardroid.common.HumanActivityType;
import org.hardroid.model.WekaModel;

/**
 * WekaClassifier implementation
 */
public class WekaModelImpl implements WekaModel {
    private HumanActivityType[] detectedActivity = new HumanActivityType[]{
        HumanActivityType.RUNNING,
        HumanActivityType.WALKING,
        HumanActivityType.ON_BICYCLE,
        HumanActivityType.IN_VEHICLE,
        HumanActivityType.STILL,
        HumanActivityType.TILTING
    };

    @Override
    public int version() {
        return WekaWrapperV1002.VERSION;
    }

    @Override
    public HumanActivityType classify(Object[] i) throws Exception {
        int result = (int) WekaWrapperV1002.classify(i);
        if (result >= 0 && result < detectedActivity.length) {
            return detectedActivity[result];
        }
        else {
            return HumanActivityType.UNKNOWN;
        }
    }
}
