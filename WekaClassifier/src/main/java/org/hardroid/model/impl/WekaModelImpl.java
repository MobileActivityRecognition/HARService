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

import org.hardroid.model.WekaModel;

/**
 * WekaClassifier implementation
 */
public class WekaModelImpl implements WekaModel {
    @Override
    public int version() {
        return WekaWrapperV1001.VERSION;
    }

    @Override
    public double classify(Object[] i) throws Exception {
        return WekaWrapperV1001.classify(i);
    }
}
