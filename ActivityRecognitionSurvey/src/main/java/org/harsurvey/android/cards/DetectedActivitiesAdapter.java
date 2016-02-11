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

package org.harsurvey.android.cards;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.survey.Constants;
import org.harsurvey.android.survey.R;

/**
 * Adapter for cards
 */
public class DetectedActivitiesAdapter extends CursorAdapter {
    private View.OnClickListener listener;

    public DetectedActivitiesAdapter(Context context, Cursor c, View.OnClickListener listener) {
        super(context, c, 0);
        this.listener = listener;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        String tag = "ACT_" + cursor.getLong(cursor.getColumnIndexOrThrow(BaseColumns._ID));
        LayoutInflater inflater = LayoutInflater.from(context);
        Activity activity = (Activity) context;
        // Inflating the card.
        ViewGroup cardView = (ViewGroup) inflater.inflate(R.layout.card,
                (ViewGroup) activity.findViewById(R.id.card_stream), false);

        // Check that the layout contains a TextView with the card_title id
        View viewTitle = cardView.findViewById(R.id.card_title);
        if (viewTitle != null) {
            viewTitle.setVisibility(View.GONE);
        }

        // Check that the layout contains a TextView with the card_content id
        View viewDesc = cardView.findViewById(R.id.card_content);
        if (viewDesc != null) {
            viewDesc.setVisibility(View.GONE);
        }

        ViewGroup actionArea = (ViewGroup) cardView.findViewById(R.id.card_actionarea);

        actionArea.setVisibility(View.VISIBLE);
        Button actionButtonPositive = (Button) actionArea.findViewById(R.id.card_button_positive);
        actionButtonPositive.setOnClickListener(listener);

        Button actionButtonNegative = (Button) actionArea.findViewById(R.id.card_button_negative);
        actionButtonNegative.setOnClickListener(listener);

        cardView.setTag(tag);

        return cardView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        HumanActivityData ha = HumanActivityData.CREATOR.createSingleFromCursor(cursor);
        TextView viewTitle = (TextView) view.findViewById(R.id.card_title);
        TextView viewDesc = (TextView) view.findViewById(R.id.card_content);

        viewTitle.setText(Constants.getActivityString(context, ha.activity));
        viewTitle.setVisibility(View.VISIBLE);
        viewTitle.setCompoundDrawablesWithIntrinsicBounds(Constants.getActivityIcon(context, ha.activity),
                null,
                null,
                null);
        viewDesc.setText(String.format("%s: %d %%",
                DateUtils.getRelativeTimeSpanString(ha.created.getTime()),
                ha.confidence));
        viewDesc.setVisibility(View.VISIBLE);
    }

}
