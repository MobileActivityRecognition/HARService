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
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import org.harsurvey.android.cards.CardStreamLinearLayout;
import org.harsurvey.android.cards.DetectedActivitiesAdapter;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.util.CardActionHelper;

/**
 * Show CardView Feed activity
 */
public class FeedActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = FeedActivity.class.getSimpleName();
    CardStreamLinearLayout listView;
    CursorLoader cursorLoader;
    private CardActionHelper cardActionHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cardstream);
        cardActionHelper = new CardActionHelper(this);
        listView = (CardStreamLinearLayout) findViewById(R.id.card_stream);
        listView.setOnCardClickListener(cardActionHelper);
        listView.setAdapter(new DetectedActivitiesAdapter(this, null, listView));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().initLoader(0, null, this);
        app.getConnection().connect();
        app.setOnTop(true);
        if (!app.getConnection().isClientConnected()) {
            Toast.makeText(this, getString(R.string.not_connected), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        app.setOnTop(false);
        getLoaderManager().destroyLoader(0);
        super.onPause();
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
