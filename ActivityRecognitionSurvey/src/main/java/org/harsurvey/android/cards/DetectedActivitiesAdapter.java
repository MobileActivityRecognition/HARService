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

import org.hardroid.common.HumanActivity;
import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.survey.FeedActivity;
import org.harsurvey.android.util.Constants;
import org.harsurvey.android.survey.R;

/**
 * Adapter for cards
 */
public class DetectedActivitiesAdapter extends CursorAdapter {

    private OnCardClickListener listener;

    public DetectedActivitiesAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return null;
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
