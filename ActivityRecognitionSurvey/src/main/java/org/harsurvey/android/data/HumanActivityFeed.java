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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.text.TextUtils;
import android.util.Log;

/**
 * Activity Feed From Database
 */
public class HumanActivityFeed {
    public static final String TAG = HumanActivityFeed.class.getSimpleName();
    private final DatabaseHelper database;

    public HumanActivityFeed(DatabaseHelper databaseHelper) {
        this.database = databaseHelper;
    }

    public HumanActivityData[] getActivityUpdates(String status) {
        HumanActivityData[] result = null;
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables(HumanActivityData.Contract.TABLE);
        if (!TextUtils.isEmpty(status)) {
            qb.appendWhere(String.format("%s = '%s'", HumanActivityData.Contract.C_STATUS, status));
        }

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = qb.query(db, HumanActivityData.Contract.ALL_COLUMNS, null, null, null, null,
                    HumanActivityData.Contract.DEFAULT_SORT);
            result = HumanActivityData.CREATOR.createFromCursor(cursor);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
        return result;
    }

    public void insertOrIgnore(HumanActivityData activityData) {
        ContentValues values =  activityData.getValues();
        Log.d(TAG, "Insert or ignore " + values);
        SQLiteDatabase db = this.database.getWritableDatabase();
        try {
            db.insertWithOnConflict(HumanActivityData.Contract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        } finally {
            db.close();
        }
    }

    public void update(HumanActivityData activityData) {
        ContentValues values =  activityData.getValues();
        Log.d(TAG, "Insert or ignore " + values);
        SQLiteDatabase db = this.database.getWritableDatabase();
        String where = String.format("%s = %d", HumanActivityData.Contract._ID, activityData.id);
        try {
            db.update(HumanActivityData.Contract.TABLE, values, where, null);
        } finally {
            db.close();
        }
    }

    public void close() {
        this.database.close();
    }

    /**
    * Drop database
    */
    public void delete() {
        SQLiteDatabase db = this.database.getWritableDatabase();
        db.delete(HumanActivityData.Contract.TABLE, null, null);
        db.close();
    }

}
