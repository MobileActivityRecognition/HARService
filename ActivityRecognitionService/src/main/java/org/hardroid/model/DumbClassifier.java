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
import org.hardroid.common.HumanActivity;

/**
 * Implements a random classifier
 */
public class DumbClassifier extends ActivityClassifier {

    @Override
    public int version() {
        return 0;
    }

    @Override
    public HumanActivity.Type classify(double[] featureData) {
        return HumanActivity.Type.UNKNOWN;
    }
}
