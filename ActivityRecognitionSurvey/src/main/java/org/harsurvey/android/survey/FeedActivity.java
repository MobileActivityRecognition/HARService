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

package org.harsurvey.android.survey;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.harsurvey.android.cards.CardStreamLinearLayout;
import org.harsurvey.android.cards.DetectedActivitiesAdapter;
import org.harsurvey.android.cards.OnCardClickListener;
import org.harsurvey.android.data.HumanActivityData;

;

/**
 * Show CardView Feed activity
 */
public class FeedActivity extends BaseActivity implements OnCardClickListener,
        LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = FeedActivity.class.getSimpleName();
    CardStreamLinearLayout listView;
    CursorLoader cursorLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cardstream);
        listView = (CardStreamLinearLayout) findViewById(R.id.card_stream);
        listView.setCardClickListener(this);
        listView.setAdapter(new DetectedActivitiesAdapter(this, null, listView));
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        app.connect();
        if (!app.isClientConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getLoaderManager().destroyLoader(0);
    }


    @Override
    public void onCardClick(int action, String tag) {
        Long id = Long.valueOf(tag.split("_")[1]);
        HumanActivityData activityData = new HumanActivityData(id);
        activityData.status = HumanActivityData.Status.PENDING;
        if (action == R.id.card_button_positive) {
            activityData.feedback = true;
        }
        else {
            activityData.feedback = false;
        }
        int updated = getContentResolver().update(
                ContentUris.withAppendedId(HumanActivityData.CONTENT_URI, id),
                activityData.getValues(), null, null);
        if (updated > 0) {
            Log.d(TAG,  String.format("Saved activity %s as %s", activityData.getId(),
                    activityData.feedback));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        cursorLoader = new CursorLoader(this, HumanActivityData.CONTENT_URI,
                HumanActivityData.Contract.ALL_COLUMNS,
                HumanActivityData.Contract.C_STATUS + " = ?",
                new String[]{ HumanActivityData.Status.DRAFT.toString() },
                HumanActivityData.Contract.DEFAULT_SORT);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        listView.getAdapter().swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listView.getAdapter().swapCursor(null);
    }
}
