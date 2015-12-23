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

import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.ViewGroup;

import org.harsurvey.android.cards.Card;
import org.harsurvey.android.cards.CardStreamFragment;
import org.harsurvey.android.cards.DetectedActivitiesAdapter;
import org.harsurvey.android.cards.OnCardClickListener;
import org.harsurvey.android.data.HumanActivityData;

import java.util.ArrayList;
import java.util.List;

/**
 * Card Stream Fragment
 */
public class FeedActivityFragment extends Fragment {
    public static final String TAG = FeedActivityFragment.class.getSimpleName();
    private FeedActivity activity;

    @Override
    public void onResume() {
        super.onResume();

        activity = (FeedActivity) getActivity();
        CardStreamFragment stream = activity.getCardStream();
        FeedActivity activity = (FeedActivity) getActivity();
        List<HumanActivityData> activities = new ArrayList<>();
        if (stream.getCardCount() < 1) {
            activities = activity.getUpdates();
            initializeCards(activities);
        }
    }

    private void initializeCards(List<HumanActivityData> activities) {
        for (HumanActivityData ha: activities) {
            activity.getCardStream().addCard(new Card.Builder(activity, "intro")
                    .setTitle(ha.activity.toString())
                    .setDescription(String.valueOf(ha.confidence))
                    .addAction("OK", 0, Card.ACTION_POSITIVE)
                    .addAction("NO OK", 1, Card.ACTION_NEGATIVE)
                    .build(this.activity)
            );
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // TODO: Listeners
    }

}
