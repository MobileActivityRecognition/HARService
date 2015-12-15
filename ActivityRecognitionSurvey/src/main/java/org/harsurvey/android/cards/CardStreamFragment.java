package org.harsurvey.android.cards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.harsurvey.android.survey.R;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * Fragments Holder
 */
public class CardStreamFragment extends Fragment {
    public LinkedHashMap<String, Card> cards = new LinkedHashMap<>();
    CardStreamLinearLayout layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cardstream, container, false);
        layout = (CardStreamLinearLayout) view.findViewById(R.id.card_stream);
        return view;
    }
    public void removeCard(Card card) {
        layout.removeView(card.getView());
    }

    public Card getCard(String tag) {
        return null;
    }

    public int getCardCount() {
        return 0;
    }

    public Collection<Card> getCards() {
        return null;
    }
}
