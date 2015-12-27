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

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

/**
 * Activity Feed From Database
 */
public class HumanActivityFeed extends ContentProvider {
    public static final String TAG = HumanActivityFeed.class.getSimpleName();
    private DatabaseHelper database;

    public static final String AUTHORITY = HumanActivityFeed.class.getCanonicalName();
    public static final int ACTIVITY_ITEM = 1;
    public static final int ACTIVITY_DIR = 2;
    public static final int FEATURE_ITEM = 3;
    public static final int FEATURE_DIR = 4;

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, HumanActivityData.Contract.TABLE, ACTIVITY_DIR);
        uriMatcher.addURI(AUTHORITY, HumanActivityData.Contract.TABLE + "/#", ACTIVITY_ITEM);
        uriMatcher.addURI(AUTHORITY, FeatureData.Contract.TABLE, FEATURE_DIR);
        uriMatcher.addURI(AUTHORITY, FeatureData.Contract.TABLE + "/#", FEATURE_ITEM);
    }


    public boolean onCreate() {
        this.database = new DatabaseHelper(getContext());
        Log.i(TAG, "Initialize database");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        int uriMatch = uriMatcher.match(uri);
        String table = HumanActivityData.Contract.TABLE;
        String defaultSortOrder = HumanActivityData.Contract.DEFAULT_SORT;
        if (uriMatch == FEATURE_DIR || uriMatch == FEATURE_ITEM) {
            table = FeatureData.Contract.TABLE;
            defaultSortOrder = FeatureData.Contract.DEFAULT_SORT;
        }
        qb.setTables(table);

        if (uriMatch == ACTIVITY_ITEM || uriMatch == FEATURE_ITEM) {
            long id = ContentUris.parseId(uri);
            qb.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
        }
        else if (uriMatch != ACTIVITY_DIR && uriMatch != FEATURE_DIR) {
            throw new IllegalArgumentException("Illegal URI");
        }

        String orderBy = (TextUtils.isEmpty(sortOrder) ?
                defaultSortOrder : sortOrder);

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d(TAG, "queried record: " + cursor.getCount());
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case ACTIVITY_DIR:
                return HumanActivityData.ACTIVITY_TYPE_DIR;
            case ACTIVITY_ITEM:
                return HumanActivityData.ACTIVITY_TYPE_ITEM;
            case FEATURE_DIR:
                return FeatureData.FEATURE_TYPE_DIR;
            case FEATURE_ITEM:
                return FeatureData.FEATURE_TYPE_ITEM;
            default:
                throw new IllegalArgumentException("Illegal uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri result = null;

        String table = null;
        switch (uriMatcher.match(uri)) {
            case ACTIVITY_DIR:
                table = HumanActivityData.Contract.TABLE;
                break;
            case FEATURE_DIR:
                table = FeatureData.Contract.TABLE;
                break;
            default:
                throw new IllegalArgumentException("Invalid URI");
        }

        SQLiteDatabase db = database.getWritableDatabase();

        try {
            long rowId = db.insertWithOnConflict(table, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);
            if (rowId != -1) {
                long id = rowId;
                result = ContentUris.withAppendedId(uri, id);
                getContext().getContentResolver().notifyChange(uri, null);
            }
        } finally {
            db.close();
        }
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String where;

        int uriMatch = uriMatcher.match(uri);
        if (uriMatch == ACTIVITY_DIR || uriMatch == FEATURE_DIR) {
            where = selection;
        }
        else if (uriMatch == ACTIVITY_ITEM || uriMatch == FEATURE_ITEM) {
            long id = ContentUris.parseId(uri);
            where = BaseColumns._ID + "=" + id +
                    (TextUtils.isEmpty(selection) ? "" : " and (" + selection + ")");
        }
        else {
            throw new IllegalArgumentException("Illegal URI");
        }

        String table = HumanActivityData.Contract.TABLE;
        if (uriMatch == FEATURE_ITEM || uriMatch == FEATURE_DIR) {
            table = FeatureData.Contract.TABLE;
        }

        SQLiteDatabase db = database.getWritableDatabase();
        try {
            int ret = db.delete(table, where, selectionArgs);

            if (ret > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            Log.d(TAG, "deleted records: " + ret);
        } finally {
            db.close();
        }

        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String where;

        int uriMatch = uriMatcher.match(uri);
        if (uriMatch == ACTIVITY_DIR || uriMatch == FEATURE_DIR) {
            where = selection;
        }
        else if (uriMatch == ACTIVITY_ITEM || uriMatch == FEATURE_ITEM) {
            long id = ContentUris.parseId(uri);
            where = BaseColumns._ID + "=" + id +
                    (TextUtils.isEmpty(selection) ? "" : " and (" + selection + ")");
        }
        else {
            throw new IllegalArgumentException("Illegal URI");
        }

        String table = HumanActivityData.Contract.TABLE;
        if (uriMatch == FEATURE_ITEM || uriMatch == FEATURE_DIR) {
            table = FeatureData.Contract.TABLE;
        }

        SQLiteDatabase db = database.getWritableDatabase();
        try {
            int ret = db.update(table, values, where, selectionArgs);

            if (ret > 0) {
                getContext().getContentResolver().notifyChange(uri, null);
            }
            Log.d(TAG, "updated records: " + ret);
        } finally {
            db.close();
        }

        return 0;
    }

}
