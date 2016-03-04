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

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Feature Calculation Information
 */
public class Feature implements Parcelable {
    private int featureSize;
    private double[] data;
    private HumanActivity.Type activityLabel;

    public Feature(HumanActivity.Type activityLabel, int featureSize, double[] data) {
        this.featureSize = featureSize;
        this.data = data;
        this.activityLabel = activityLabel;
    }

    public HumanActivity.Type getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(HumanActivity.Type activityLabel) {
        this.activityLabel = activityLabel;
    }

    public double[] getData() {
        return data;
    }

    public void setData(double[] data) {
        this.data = data;
    }

    public int getFeatureSize() {
        return featureSize;
    }

    public void setFeatureSize(int featureSize) {
        this.featureSize = featureSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.activityLabel.ordinal());
        parcel.writeInt(this.featureSize);
        parcel.writeDoubleArray(data);
    }

    public static final Parcelable.Creator<Feature> CREATOR = new Creator<Feature>() {
        @Override
        public Feature createFromParcel(Parcel parcel) {
            HumanActivity.Type type = HumanActivity.Type.values()[parcel.readInt()];
            int featureSize = parcel.readInt();
            double[] data = new double[featureSize];
            parcel.readDoubleArray(data);
            return new Feature(type, featureSize, data);
        }

        @Override
        public Feature[] newArray(int size) {
            return new Feature[size];
        }
    };
}
