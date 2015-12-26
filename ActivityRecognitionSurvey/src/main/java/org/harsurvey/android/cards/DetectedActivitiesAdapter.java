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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.harsurvey.android.data.HumanActivityData;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for cards
 */
public class DetectedActivitiesAdapter extends ArrayAdapter<HumanActivityData> {
    private final OnCardClickListener listener;
    private ArrayList<Card> cards = new ArrayList<>();

    public DetectedActivitiesAdapter(Context context, List<HumanActivityData> activities,
                                     OnCardClickListener listener) {
        super(context, 0, activities);
        this.listener = listener;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        HumanActivityData ha = getItem(position);
        if (view == null) {
            if (position > cards.size()) {
                Card card = new Card.Builder(listener, "ACTIVITY_" + ha.getId())
                        .setTitle(ha.activity.toString())
                        .setDescription(String.valueOf(ha.confidence))
                        .addAction("OK", 0, Card.ACTION_POSITIVE)
                        .addAction("NO OK", 1, Card.ACTION_NEGATIVE)
                        .build((Activity) this.getContext());
                cards.add(card);
            }
            view = cards.get(position).getView();
        }
        return view;
    }

    public void addActivity(HumanActivityData data) {
        this.add(data);
    }
}
