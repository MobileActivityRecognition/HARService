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

package org.harsurvey.android.util;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.harsurvey.android.cards.Card;
import org.harsurvey.android.cards.CardListItemAdapter;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.survey.AccountSettings;
import org.harsurvey.android.survey.FeedActivity;
import org.harsurvey.android.survey.R;

/**
 * Contiene la logica para manejo de Cards en la aplicación
 */
public class CardActionHelper implements Card.OnClickListener, Card.OnDismissListener {
    private static final String TAG = CardActionHelper.class.getSimpleName();

    private FeedActivity feed;
    private CardListItemAdapter cardListItemAdapter;

    public CardActionHelper(FeedActivity context) {
        this.feed = context;
        this.cardListItemAdapter = new CardListItemAdapter(context, Constants.ACTIVITY_LIST);
    }

    @Override
    public void onClick(int action, String tag) {
        if (tag.startsWith("ACT_")) {
            handleSurveyCard(action, tag);
        }
        else if (tag.equalsIgnoreCase(Constants.INTRO_CARD)) {
            feed.startActivity(new Intent(feed, AccountSettings.class));
        }
        else {
            Log.i(TAG, "Nothing to be done for tag: " + tag);
        }
    }

    @Override
    public void onDismiss(String tag) {
        if (tag.startsWith("ACT_")) {
            HumanActivityData activityData = new HumanActivityData(getIdFromTag(tag));
            activityData.status = HumanActivityData.Status.CANCEL;
            activityData.feedback = false;
            feed.removeCard(tag);
            saveAndSync(activityData, false);
        }
    }

    public void handleSurveyCard(int action, final String tag) {
        Long id = getIdFromTag(tag);
        boolean checkButtom = (action == Card.ACTION_POSITIVE);
        final HumanActivityData activityData = new HumanActivityData(id);
        if (checkButtom) {
            activityData.status = HumanActivityData.Status.PENDING;
            activityData.feedback = true;
            feed.removeCard(tag);
            saveAndSync(activityData, true);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(feed);
            builder.setTitle(R.string.pick_activity_title)
                   .setAdapter(cardListItemAdapter, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           activityData.feedbackActivity = Constants.ACTIVITY_LIST[i];
                           if (i < 5) { // Desconocido no se sincroniza
                               activityData.status = HumanActivityData.Status.PENDING;
                           } else {
                               activityData.status = HumanActivityData.Status.CANCEL;
                           }
                           activityData.feedback = false;
                           CardActionHelper.this.feed.removeCard(tag);
                           CardActionHelper.this.saveAndSync(activityData, true);
                       }
                   })
                   .setCancelable(true)
                   .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           Log.i(TAG, "Cancel activity survey dialog");
                       }
                   });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    private void saveAndSync(HumanActivityData activityData, boolean sync) {
        int updated = feed.getContentResolver().update(
                ContentUris.withAppendedId(HumanActivityData.CONTENT_URI, activityData.getId()),
                activityData.getValues(), null, null);
        if (updated > 0) {
            Log.d(TAG, String.format("Saved activity %s as %s, status %s", activityData.getId(),
                    activityData.feedback, activityData.status));
            if (feed.app.isOnline() && sync) {
                Intent localIntent = new Intent(Constants.REQUEST_SYNCRONIZATION);
                feed.sendBroadcast(localIntent);
            }
        }
    }

    private Long getIdFromTag(String tag) {
        return Long.valueOf(tag.split("_")[1]);
    }
}
