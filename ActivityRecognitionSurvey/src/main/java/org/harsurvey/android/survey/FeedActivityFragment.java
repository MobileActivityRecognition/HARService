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

import android.app.Fragment;

import org.harsurvey.android.cards.Card;
import org.harsurvey.android.cards.CardStreamFragment;
import org.harsurvey.android.cards.OnCardClickListener;

/**
 * Card Stream Fragment
 */
public class FeedActivityFragment extends Fragment implements OnCardClickListener {
    public static final String TAG = FeedActivity.class.getSimpleName();
    private CardStreamFragment cards;

    @Override
    public void onResume() {
        super.onResume();

        CardStreamFragment stream = getCardStream();
        if (stream.getCardCount() < 1) {
            initializeCards();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // TODO: Listeners
    }

    private void initializeCards() {
        Card c = new Card.Builder(this, "intro")
                .setTitle(getString(R.string.intro_title))
                .setDescription(getString(R.string.intro_message))
                .build(getActivity());
        getCardStream().addCard(c);
    }


    public CardStreamFragment getCardStream() {
        if (cards == null) {
            cards = ((FeedActivity) getActivity()).getCardStream();
        }
        return cards;
    }

    @Override
    public void onCardClick(int id, String tag) {

    }
}
