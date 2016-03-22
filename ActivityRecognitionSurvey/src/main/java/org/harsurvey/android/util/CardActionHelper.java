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

import org.harsurvey.android.cards.CardListItemAdapter;
import org.harsurvey.android.cards.OnCardClickListener;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.survey.FeedActivity;
import org.harsurvey.android.survey.R;

/**
 * Contiene la logica para manejo de Cards en la aplicación
 */
public class CardActionHelper implements OnCardClickListener {
    private static final String TAG = CardActionHelper.class.getSimpleName();

    private FeedActivity activity;
    private CardListItemAdapter cardListItemAdapter;

    public CardActionHelper(FeedActivity context) {
        this.activity = context;
        this.cardListItemAdapter = new CardListItemAdapter(context, Constants.ACTIVITY_LIST);
    }

    @Override
    public void onCardClick(int action, String tag) {
        if (tag.startsWith("ACT_")) {
            handleSurveyCard(action, tag.split("_")[1]);
            activity.removeCard(tag);
        }
        else {
            Log.i(TAG, "Nothing to be done for tag: " + tag);
        }
    }

    public void handleSurveyCard(int action, String tag) {
        Long id = Long.valueOf(tag);
        boolean checkButtom = (action == Constants.ACTION_POSITIVE);
        final HumanActivityData activityData = new HumanActivityData(id);
        if (checkButtom) {
            activityData.status = HumanActivityData.Status.PENDING;
            activityData.feedback = true;
            saveAndSync(activityData, true);
        }
        else {
            activityData.feedback = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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

    public void saveAndSync(HumanActivityData activityData, boolean sync) {
        int updated = activity.getContentResolver().update(
                ContentUris.withAppendedId(HumanActivityData.CONTENT_URI, activityData.getId()),
                activityData.getValues(), null, null);
        if (updated > 0) {
            Log.d(TAG, String.format("Saved activity %s as %s", activityData.getId(),
                    activityData.feedback));
            if (activity.app.isOnline() && sync) {
                Intent localIntent = new Intent(Constants.REQUEST_SYNCRONIZATION);
                activity.sendBroadcast(localIntent);
            }
        }
    }
}
