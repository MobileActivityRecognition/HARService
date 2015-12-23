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
;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import org.harsurvey.android.cards.CardStream;
import org.harsurvey.android.cards.CardStreamFragment;
import org.harsurvey.android.cards.OnCardClickListener;
import org.harsurvey.android.data.HumanActivityData;

import java.util.List;

/**
 * Show CardView Feed activity
 */
public class FeedActivity extends BaseActivity implements CardStream, OnCardClickListener {
    private CardStreamFragment cardStreamFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.feed_activity);

        FragmentManager fm = getSupportFragmentManager();
        FeedActivityFragment fragment = (FeedActivityFragment)
                fm.findFragmentByTag(FeedActivityFragment.TAG);

        if (fragment == null) {
            fm.beginTransaction()
                    .add(new FeedActivityFragment(), FeedActivityFragment.TAG)
                    .commit();
        }
    }


    @Override
    public CardStreamFragment getCardStream() {
        if (cardStreamFragment == null) {
            cardStreamFragment = (CardStreamFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_cardstream);
        }
        return cardStreamFragment;
    }

    public List<HumanActivityData> getUpdates() {
        return app.getActivitiesUpdates();
    }

    @Override
    public void onCardClick(int id, String tag) {

    }
}
