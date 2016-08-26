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

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import org.harsurvey.android.data.HumanActivityData;
import org.harsurvey.android.survey.R;
import org.harsurvey.android.util.Constants;

import at.grabner.circleprogress.CircleProgressView;

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
        TextView viewDate = (TextView) view.findViewById(R.id.card_date);
        TextView viewTitle = (TextView) view.findViewById(R.id.card_title);
        TextView viewDesc = (TextView) view.findViewById(R.id.card_content);

        viewTitle.setText(Constants.getActivityString(context, ha.activity));

        Drawable image = Constants.getActivityIcon(context, ha.activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            viewTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(image, null, null, null);
        } else {
            viewTitle.setCompoundDrawablesWithIntrinsicBounds(image, null, null, null);
        }

        CircleProgressView progressView = (CircleProgressView) view.findViewById(R.id.progressBar);
        if (ha.confidence < 50) {
            progressView.setBarColor(Color.parseColor(Constants.getStringResource(context, R.color.bittersweet)));
        }
        else if (ha.confidence < 80) {
            progressView.setBarColor(Color.parseColor(Constants.getStringResource(context, R.color.sunflower)));
        }
        progressView.setValue(ha.confidence);
        progressView.setVisibility(View.VISIBLE);

        long time = ha.created.getTime();
        viewDate.setText(DateUtils.formatDateTime(context, time, DateUtils.FORMAT_SHOW_TIME));
        viewDesc.setText(DateUtils.getRelativeTimeSpanString(time));
    }

}
