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

import android.text.TextUtils;
import android.util.Log;

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
    private static final String TAG = DecisionTreeClassifier.class.getSimpleName();

    private WekaClassifier classifier;
    private WekaClassifier defaultClassifier = new FallbackClassifier();

    public void setClassifier(WekaClassifier classifier) {
        this.classifier = classifier;
    }

    @Override
    public int version() {
        if (classifier != null) {
            return classifier.version();
        }
        else {
            return defaultClassifier.version();
        }
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
            if (classifier != null) {
                result = (int) classifier.classify(sendData);
            }
            else {
                result = (int) defaultClassifier.classify(sendData);
            }
            if (result > 0 && result < detectedActivity.length) {
                return detectedActivity[result];
            }
            else {
                Log.e(TAG, "Error al detectar actividad, resultado invalido: " + result);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error al detectar actividad: " + e.getMessage());
        }
        return Type.UNKNOWN;
    }

    private class FallbackClassifier implements WekaClassifier {

        @Override
        public int version() {
            return WekaWrapperV1001.VERSION;
        }

        @Override
        public double classify(Object[] i) throws Exception {
            return WekaWrapperV1001.classify(i);
        }
    }
}
