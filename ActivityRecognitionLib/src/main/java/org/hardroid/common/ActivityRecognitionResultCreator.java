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

package org.hardroid.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Creates a Java object from android parcel
 *
 * @author agimenez
 */
public class ActivityRecognitionResultCreator
        implements Parcelable.Creator<ActivityRecognitionResult> {
    @Override
    public ActivityRecognitionResult createFromParcel(Parcel parcel) {
        int modelVersion = parcel.readInt();
        int size = parcel.readInt();
        ArrayList<HumanActivity> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(HumanActivity.CREATOR.createFromParcel(parcel));
        }
        long time = parcel.readLong();
        long elapsedRealtime = parcel.readLong();
        ActivityRecognitionResult result = new ActivityRecognitionResult(modelVersion,
                list, time, elapsedRealtime);
        long featSize = parcel.readLong();
        if (featSize > 0) {
            ArrayList<Feature> features = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                features.add(Feature.CREATOR.createFromParcel(parcel));
            }
            result.setFeatures(features);
        }
        return result;
    }

    @Override
    public ActivityRecognitionResult[] newArray(int size) {
        return new ActivityRecognitionResult[size];
    }
}
