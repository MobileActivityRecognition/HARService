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

package org.harservice.android.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 *
 */
public class ActivityRecognitionResultCreator
        implements Parcelable.Creator<ActivityRecognitionResult> {
    @Override
    public ActivityRecognitionResult createFromParcel(Parcel parcel) {
        int size = parcel.readInt();
        ArrayList<HumanActivity> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(HumanActivity.CREATOR.createFromParcel(parcel));
        }
        long time = parcel.readLong();
        long elapsedRealtime = parcel.readLong();
        return new ActivityRecognitionResult(list, time, elapsedRealtime);
    }

    @Override
    public ActivityRecognitionResult[] newArray(int size) {
        return new ActivityRecognitionResult[size];
    }
}
