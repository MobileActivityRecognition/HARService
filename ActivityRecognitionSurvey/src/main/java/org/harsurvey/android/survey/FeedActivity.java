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
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import org.hardroid.common.HumanActivity;
import org.harsurvey.android.cards.Card;
import org.harsurvey.android.cards.CardStreamLinearLayout;
import org.harsurvey.android.cards.DetectedActivitiesAdapter;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.util.CardActionHelper;
import org.harsurvey.android.util.Constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Show CardView Feed activity
 */
public class FeedActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final String TAG = FeedActivity.class.getSimpleName();

    CardStreamLinearLayout listView;
    CursorLoader cursorLoader;
    CardActionHelper cardActionHelper;
    DetectedActivitiesAdapter adapter;
    Map<String, Card> cards = new LinkedHashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cardActionHelper = new CardActionHelper(this);
        listView = (CardStreamLinearLayout) findViewById(R.id.card_stream);
        adapter = new DetectedActivitiesAdapter(this, null);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                addSurveyCards();
            }
        });
        getLoaderManager().initLoader(0, null, this);
        listView.showInitialAnimation = true;
        setToolbarTitle(R.string.activity_title);
    }

    private void addSurveyCards() {
        Cursor cursor = adapter.getCursor();
        View view;
        int last = cursor.getCount();
        long lastTime = -1;
        if (last > Constants.MAX_CARDS) {
            int first = last - Constants.MAX_CARDS;
            cursor.moveToPosition(first - 1);
        }
        while(cursor.moveToNext()) {
            String tag = "ACT_" + cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
            boolean isNew = !cards.containsKey(tag);
            int colIndex = cursor.getColumnIndex(HumanActivityData.Contract.C_ACTIVITY_TYPE);
            HumanActivity.Type activity = HumanActivity.Type.valueOf(cursor.getString(colIndex));
            Card card;
            if (isNew) {
                card = new Card.Builder(cardActionHelper, tag)
                        .setTitle("")
                        .setDescription("")
                        .addAction(Constants.getStringResource(this, R.string.action_ok),
                                Card.ACTION_POSITIVE, Card.ACTION_POSITIVE)
                        .addAction(Constants.getStringResource(this, R.string.action_nook),
                                Card.ACTION_NEGATIVE, Card.ACTION_NEGATIVE)
                        .build(this);
                view = card.getView();
                addCard(card, true);
            }
            else {
                card = cards.get(tag);
                view = card.getView();
            }
            adapter.bindView(view, this, cursor);
            if (activity.equals(HumanActivity.Type.UNKNOWN)) {
                card.hideAction(Card.ACTION_POSITIVE);
            }
            if (lastTime < 0 && last >= Constants.MAX_CARDS) {
                lastTime = cursor.getLong(cursor.getColumnIndex(HumanActivityData.Contract.C_CREATED));
            }
        }
        if (lastTime > 0) {
            cursorLoader.setSelectionArgs(new String[]{
                    HumanActivityData.Status.DRAFT.toString() ,
                    String.valueOf(lastTime)
            });
            app.getPreference().setLastUpdateTime(lastTime);
        }
    }

    private void showIntroCard() {
        boolean isNew = !cards.containsKey(Constants.INTRO_CARD);
        if (isNew) {
            Card card = new Card.Builder(cardActionHelper, Constants.INTRO_CARD)
                    .setTitle(getString(R.string.intro_title))
                    .setDescription(getString(R.string.intro_message))
                    .addAction(Constants.getStringResource(this, R.string.action_ready),
                            Card.ACTION_NEUTRAL, Card.ACTION_NEUTRAL)
                    .build(this);
            addCard(card, false);
        }
    }

    public void addCard(Card card, boolean dismiss) {
        cards.put(card.getTag(), card);
        listView.addCard(card.getView(), dismiss);
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
            if (app.getPreference().getServiceStatus() && !app.getConnection().isClientConnected()) {
                setDetectorService(true);
            }
            else {
                setActionButtonStatus();
            }
            setNavigationAccount(app.getPreference().getName());
        }
        else {
            setActionButtonVisibility(false);
            this.showIntroCard();
        }
        app.setOnTop(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        app.setOnTop(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        long delta = app.getPreference().getLastUpdateTime();
        cursorLoader = new CursorLoader(this, HumanActivityData.CONTENT_URI,
                HumanActivityData.Contract.ALL_COLUMNS,
                HumanActivityData.Contract.C_STATUS + " = ? AND " +
                HumanActivityData.Contract.C_CREATED + " >= ? ",
                new String[]{
                        HumanActivityData.Status.DRAFT.toString() ,
                        String.valueOf(delta)
                },
                HumanActivityData.Contract.C_CREATED + " ASC");
        if (app.hasValidAccount()) {
            showInfoToast(String.format(Constants.getStringResource(this, R.string.date_info_msg),
                    Constants.formatShortDate(this, delta)));
        }
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

    public void showInfoToast(String msg) {
        Toast info = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        info.setGravity(Gravity.TOP | Gravity.CENTER_VERTICAL, 0, 0);
        info.show();
    }
}
