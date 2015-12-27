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
import android.database.DatabaseUtils;
import android.net.Uri;
import android.provider.BaseColumns;

import org.harservice.android.common.HumanActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Collected Human Activity Data
 */
public class HumanActivityData {

    // PROVIDER
    public static final Uri CONTENT_URI = Uri.parse("content://" +
            HumanActivityFeed.AUTHORITY + "/" + Contract.TABLE);
    public static final String ACTIVITY_TYPE_DIR =
            "vnd.android.cursor.dir/org.harsurvey.android.data." + Contract.TABLE;
    public static final String ACTIVITY_TYPE_ITEM =
            "vnd.android.cursor.item/org.harsurvey.android.data." + Contract.TABLE;

    public enum Status {
        DRAFT,
        PENDING,
        SAVED
    }

    // FIELDS
    protected long id = -1;
    public Date created;
    public HumanActivity.Type activity;
    public int confidence = -1;
    public Status status;
    public Boolean feedback;

    public HumanActivityData(long id) {
        this.id = id;
    }

    public HumanActivityData(Date created, HumanActivity.Type activity,
                             int confidence, Status status, boolean feedback) {
        this.created = created;
        this.activity = activity;
        this.confidence = confidence;
        this.status = status;
        this.feedback = feedback;
    }

    public HumanActivityData(ContentValues values) {
        this.update(values);
    }

    public void update(ContentValues values) {
        if (values.containsKey(Contract._ID)) {
            id = values.getAsLong(Contract._ID);
        }
        if (values.containsKey(Contract.C_CREATED)) {
            created = new Date(values.getAsLong(Contract.C_CREATED));
        }
        if (values.containsKey(Contract.C_ACTIVITY_TYPE)) {
            activity = HumanActivity.Type.valueOf(values.getAsString(Contract.C_ACTIVITY_TYPE));
        }
        if (values.containsKey(Contract.C_ACTIVITY_CONFIDENCE)) {
            confidence = values.getAsInteger(Contract.C_ACTIVITY_CONFIDENCE);
        }
        if (values.containsKey(Contract.C_STATUS)) {
            status = Status.valueOf(values.getAsString(Contract.C_STATUS));
        }
        if (values.containsKey(Contract.C_FEEDBACK)) {
            feedback = values.getAsBoolean(Contract.C_FEEDBACK);
        }
    }

    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        if (id > 1) {
            values.put(Contract._ID, id);
        }
        if (created != null) {
            values.put(Contract.C_CREATED, created.getTime());
        }
        if (activity != null) {
            values.put(Contract.C_ACTIVITY_TYPE, activity.toString());
        }
        if (confidence > 0) {
            values.put(Contract.C_ACTIVITY_CONFIDENCE, confidence);
        }
        if (status != null) {
            values.put(Contract.C_STATUS, status.toString());
        }
        if (feedback != null) {
            values.put(Contract.C_FEEDBACK, feedback);
        }
        return values;
    }

    public long getId() {
        return id;
    }

    public static final class Contract implements BaseColumns {
        public static final String TABLE = "activity_feed";
        public static final String C_CREATED = "created_at";
        public static final String C_ACTIVITY_TYPE = "activity_type";
        public static final String C_ACTIVITY_CONFIDENCE = "activity_confidence";
        public static final String C_STATUS = "status";
        public static final String C_FEEDBACK = "feedback";

        public static final String[] ALL_COLUMNS = {
                _ID, C_CREATED, C_ACTIVITY_TYPE, C_ACTIVITY_CONFIDENCE, C_STATUS, C_FEEDBACK
        };

        public static final String DEFAULT_SORT = C_CREATED + " ASC";

        public static final String SQL_CREATE = String.format(
          "CREATE TABLE %s (" +
          "%s INTEGER PRIMARY KEY, " +
          "%s INTEGER, " +
          "%s TEXT," +
          "%s INTEGER," +
          "%s TEXT," +
          "%s INTEGER)", TABLE, _ID, C_CREATED, C_ACTIVITY_TYPE, C_ACTIVITY_CONFIDENCE,
                C_STATUS, C_FEEDBACK);

        public static final String SQL_DROP = "DROP TABLE IF EXITS " + TABLE;
    }

    public static final DataCreator<HumanActivityData> CREATOR = new DataCreator<>(HumanActivityData.class);
}
