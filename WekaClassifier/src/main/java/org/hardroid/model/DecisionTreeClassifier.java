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

package org.hardroid.model;

import org.hardroid.common.ActivityClassifier;
import org.hardroid.common.HumanActivity.Type;

/**
 * Implements a decision tree classifier
 */
public class DecisionTreeClassifier extends ActivityClassifier {
    private Type[] detectedActivity = new Type[]{
            Type.ON_BICYCLE,
            Type.WALKING,
            Type.RUNNING,
            Type.STILL,
            Type.TILTING,
            Type.ON_BICYCLE,
    };

    @Override
    public int version() {
        return WekaWrapperV1001.VERSION;
    }

    @Override
    public Type classify(double[] featureData) {
        int result = -1;
        Double[] sendData = new Double[featureData.length];
        try {
            int i = 0;
            for (double feature : featureData) {
                sendData[i++] = feature;
            }
            result = (int) WekaWrapperV1001.classify(sendData);
            if (result > 0 && result < detectedActivity.length) {
                return detectedActivity[result];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Type.UNKNOWN;
    }
}
