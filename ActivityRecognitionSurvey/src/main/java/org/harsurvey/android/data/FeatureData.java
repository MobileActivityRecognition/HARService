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

package org.harsurvey.android.data;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * Feature Class Data
 */
public class FeatureData {
    // FIELDS
    protected long id = -1;
    public long activityId;
    public double mean;
    public double std;
    public double max;
    public double min;
    public double skew;
    public double kurt;
    public double energy;
    public double entropy;
    public double irq;
    public double sma;

    public static enum Variables {
        MEAN, STD, MAX, MIN, SKEWNESS,
        KURTOSIS, ENERGY, ENTROPY, IQR, SMA;

    }

    public FeatureData(long id) {
        this.id = id;
    }

    public FeatureData(long activityId, double mean, double std, double max, double min,
                       double skew, double kurt, double energy, double entropy, double irq,
                       double sma) {
        this.activityId = activityId;
        this.mean = mean;
        this.std = std;
        this.max = max;
        this.min = min;
        this.skew = skew;
        this.kurt = kurt;
        this.energy = energy;
        this.entropy = entropy;
        this.irq = irq;
        this.sma = sma;
    }

    public FeatureData(ContentValues values) {
        this.update(values);
    }

    public void update(ContentValues values) {
        for (String label: values.keySet()) {
            switch (label) {
                case Contract._ID: this.id = values.getAsLong(label); break;
                case Contract.C_ACTIVITY_ID: this.id = values.getAsLong(label); break;
                case Contract.C_MEAN: this.mean = values.getAsDouble(label); break;
                case Contract.C_STD: this.std = values.getAsDouble(label); break;
                case Contract.C_MAX: this.max = values.getAsDouble(label); break;
                case Contract.C_MIN: this.min = values.getAsDouble(label); break;
                case Contract.C_SKEWNESS: this.skew = values.getAsDouble(label); break;
                case Contract.C_ENERGY: this.energy = values.getAsDouble(label); break;
                case Contract.C_ENTROPY: this.entropy = values.getAsDouble(label); break;
                case Contract.C_IQR: this.irq = values.getAsDouble(label); break;
                case Contract.C_SMA: this.sma = values.getAsDouble(label); break;
            }
        }
    }

    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        if (this.id > 1) {
            values.put(Contract._ID, this.id);
        }
        values.put(Contract.C_ACTIVITY_ID, this.id);
        values.put(Contract.C_MEAN, this.mean);
        values.put(Contract.C_STD, this.std);
        values.put(Contract.C_MAX, this.max);
        values.put(Contract.C_MIN, this.min);
        values.put(Contract.C_SKEWNESS, this.skew);
        values.put(Contract.C_ENERGY, this.energy);
        values.put(Contract.C_ENTROPY, this.entropy);
        values.put(Contract.C_IQR, this.irq);
        values.put(Contract.C_SMA, this.sma);
        return values;
    }

    public long getId() {
        return id;
    }

    public static final class Contract implements BaseColumns {
        public static final String TABLE = "activity_feature";
        public static final String C_ACTIVITY_ID = "activity_id";
        public static final String C_MEAN = "mean";
        public static final String C_STD = "std";
        public static final String C_MAX = "max";
        public static final String C_MIN = "min";
        public static final String C_SKEWNESS = "skewness";
        public static final String C_KURTOSIS = "kurtosis";
        public static final String C_ENERGY = "energy";
        public static final String C_ENTROPY = "entropy";
        public static final String C_IQR = "irq";
        public static final String C_SMA = "sma";

        public static final String[] ALL_COLUMNS = {
            _ID, C_ACTIVITY_ID, C_MEAN, C_STD, C_MAX, C_MIN, C_SKEWNESS,
            C_KURTOSIS, C_ENERGY, C_ENTROPY, C_IQR, C_SMA
        };

        public static final String DEFAULT_SORT = _ID + " DESC";

        public static final String SQL_CREATE = String.format(
            "CREATE TABLE %s (" +
            "%s INTEGER PRIMARY KEY, " +
            "%s INTEGER, " +
            "%s REAL," +
            "%s REAL," +
            "%s REAL," +
            "%s REAL," +
            "%s REAL," +
            "%s REAL," +
            "%s REAL," +
            "%s REAL," +
            "%s REAL," +
            "%s REAL)", TABLE, _ID, C_ACTIVITY_ID, C_MEAN, C_STD, C_MAX, C_MIN,
                C_SKEWNESS, C_KURTOSIS, C_ENERGY, C_ENTROPY, C_IQR, C_SMA);

        public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE;
    }

    public static final DataCreator<FeatureData> CREATOR = new DataCreator<>(FeatureData.class);
}
