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

import android.util.Log;

import org.hardroid.common.ActivityClassifier;
import org.hardroid.common.HumanActivity.Type;

/**
 * Implements a decision tree classifier
 */
public class DecisionTreeClassifier extends ActivityClassifier {
    private Type[] detectedActivity = new Type[]{
            Type.UNKNOWN,
            Type.WALKING,
            Type.RUNNING,
            Type.STILL,
            Type.TILTING,
            Type.ON_BICYCLE,
    };
    private static final String TAG = DecisionTreeClassifier.class.getSimpleName();

    private WekaClassifier classifier;

    public DecisionTreeClassifier(WekaClassifier classifier) {
        this.classifier = classifier;
        Log.i(TAG, "Inicializando modelo " + this.toString());
    }

    @Override
    public int version() {
        return classifier.version();
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
            result = (int) classifier.classify(sendData);
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
}
