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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.harsurvey.android.util.Constants;

/**
 * Generic Database Helper
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TAG = DatabaseHelper.class.getSimpleName();

    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Creating database: " + Constants.DATABASE_NAME);
        db.execSQL(HumanActivityData.Contract.SQL_CREATE);
        db.execSQL(FeatureData.Contract.SQL_CREATE);
        // TODO: Incluir otros modelos
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL(HumanActivityData.Contract.SQL_DROP);
        db.execSQL(FeatureData.Contract.SQL_DROP);
        // TODO: Incluir otros modelos
        this.onCreate(db);
    }
}
