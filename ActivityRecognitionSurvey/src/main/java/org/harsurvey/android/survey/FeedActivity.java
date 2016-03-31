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
import android.database.DataSetObserver;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;

import org.harsurvey.android.cards.Card;
import org.harsurvey.android.cards.CardStreamLinearLayout;
import org.harsurvey.android.cards.DetectedActivitiesAdapter;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.util.CardActionHelper;
import org.harsurvey.android.util.Constants;

import java.util.Map;
import java.util.TreeMap;

/**
 * Show CardView Feed activity
 */
public class FeedActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = FeedActivity.class.getSimpleName();

    CardStreamLinearLayout listView;
    CursorLoader cursorLoader;
    CardActionHelper cardActionHelper;
    DetectedActivitiesAdapter adapter;
    Map<String, Card> cards = new TreeMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.cardstream);
        cardActionHelper = new CardActionHelper(this);
        listView = (CardStreamLinearLayout) findViewById(R.id.card_stream);
        adapter = new DetectedActivitiesAdapter(this, null);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                addSurveyCards();
            }
        });
    }

    private void addSurveyCards() {
        Cursor cursor = adapter.getCursor();
        View view = null;
        while(cursor.moveToNext()) {
            String tag = "ACT_" + cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            boolean isNew = !cards.containsKey(tag);
            if (isNew) {
                Card card = new Card.Builder(cardActionHelper, tag)
                        .setTitle("")
                        .setDescription("")
                        .addAction(Constants.getStringResource(this, R.string.action_ok),
                                Constants.ACTION_POSITIVE, Card.ACTION_POSITIVE)
                        .addAction(Constants.getStringResource(this, R.string.action_nook),
                                Constants.ACTION_NEGATIVE, Card.ACTION_NEGATIVE)
                        .build(this);
                addCard(card);
                view = card.getView();
            }
            else {
                view = cards.get(tag).getView();
            }
            adapter.bindView(view, this, cursor);
        }
    }

    private void showIntroCard() {
        boolean isNew = !cards.containsKey(Constants.INTRO_CARD);
        if (isNew) {
            Card card = new Card.Builder(cardActionHelper, Constants.INTRO_CARD)
                    .setTitle(getString(R.string.intro_title))
                    .setDescription(getString(R.string.intro_message))
                    .addAction(Constants.getStringResource(this, R.string.action_ready),
                            Constants.ACTION_NEUTRAL, Card.ACTION_NEUTRAL)
                    .build(this);
            addCard(card);
        }
    }

    public void addCard(Card card) {
        cards.put(card.getTag(), card);
        listView.addCard(card.getView());
    }

    public void removeCard(String tag) {
        Card card = cards.remove(tag);
        if (card != null) {
            listView.removeCard(card.getView());
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (app.hasValidAccount()) {
            this.resumeSurvey();
        }
        else {
            this.showIntroCard();
        }
        app.setOnTop(true);
    }

    private void resumeSurvey() {
        app.getConnection().connect();
        getLoaderManager().initLoader(0, null, this);
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
        adapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
