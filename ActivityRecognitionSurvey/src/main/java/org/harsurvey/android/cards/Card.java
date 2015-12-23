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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.harsurvey.android.survey.R;

import java.util.ArrayList;

/**
 * Card
 */
public class Card {
    public static final int ACTION_POSITIVE = 1;
    public static final int ACTION_NEGATIVE = 2;

    public static final int CARD_STATE_NORMAL = 1;
    public static final int CARD_STATE_FOCUSED = 2;
    public static final int CARD_STATE_INACTIVE = 3;

    private String tag = null;
    private String title = null;
    private String description = null;
    private int status = CARD_STATE_NORMAL;

    private View cardView = null;
    private View overlayView = null;
    private TextView titleView = null;
    private TextView descView = null;
    private View actionAreaView = null;
    private int layoutId = R.layout.card;

    private ArrayList<CardAction> cardActions = new ArrayList<CardAction>();

    private OnCardClickListener clickListener;

    public View getView() {
        return cardView;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public Card setStatus(int status) {
        this.status = status;
        return this;
    }

    private void addAction(String label, int id, int type) {
        CardAction cardAction = new CardAction();
        cardAction.label = label;
        cardAction.id = id;
        cardAction.type = type;
        cardActions.add(cardAction);
    }

    public Card setActionVisibility(int actionId, boolean isVisible) {
        int visibilityFlag = isVisible ? View.VISIBLE : View.GONE;
        for (CardAction action : cardActions) {
            if (action.id == actionId && action.actionView != null) {
                action.actionView.setVisibility(visibilityFlag);
            }
        }
        return this;
    }

    public class CardAction {
        public String label;
        public int id;
        public int type;
        public View actionView;
    }

    public static class Builder {
        private Card card;

        protected Builder(OnCardClickListener listener, Card card) {
            this.card = card;
            card.clickListener = listener;
        }

        public Builder(OnCardClickListener listener, String tag) {
            card = new Card();
            card.tag = tag;
            card.clickListener = listener;
        }

        public Builder setTitle(String title) {
            card.title = title;
            return this;
        }

        public Builder setDescription(String description) {
            card.description = description;
            return this;
        }

        public Builder addAction(String label, int id, int type) {
            card.addAction(label, id, type);
            return this;
        }

        public Builder setLayout(int layoutId) {
            card.layoutId = layoutId;
            return this;
        }

        public Builder setStatus(int status) {
            card.status = status;
            return this;
        }

        public Card build(Activity activity) {
            LayoutInflater inflater = LayoutInflater.from(activity);
            // Inflating the card.
            ViewGroup cardView = (ViewGroup) inflater.inflate(card.layoutId,
                    (ViewGroup) activity.findViewById(R.id.card_stream), false);

            // Check that the layout contains a TextView with the card_title id
            View viewTitle = cardView.findViewById(R.id.card_title);
            if (card.title != null && viewTitle != null) {
                card.titleView = (TextView) viewTitle;
                card.titleView.setText(card.title);
            } else if (viewTitle != null) {
                viewTitle.setVisibility(View.GONE);
            }

            // Check that the layout contains a TextView with the card_content id
            View viewDesc = cardView.findViewById(R.id.card_content);
            if (card.description != null && viewDesc != null) {
                card.descView = (TextView) viewDesc;
                card.descView.setText(card.description);
            } else if (viewDesc != null) {
                cardView.findViewById(R.id.card_content).setVisibility(View.GONE);
            }

            ViewGroup actionArea = (ViewGroup) cardView.findViewById(R.id.card_actionarea);

            // Inflate all action views.
            initializeActionViews(inflater, cardView, actionArea);

            card.cardView= cardView;
            card.overlayView = cardView.findViewById(R.id.card_overlay);

            return card;
        }

        private void initializeActionViews(LayoutInflater inflater, ViewGroup cardView, ViewGroup actionArea) {
            if (!card.cardActions.isEmpty()) {
                // Set action area to visible only when actions are visible
                actionArea.setVisibility(View.VISIBLE);
                card.actionAreaView = actionArea;
            }

            // Inflate all card actions
            for (final CardAction action : card.cardActions) {

                int useActionLayout = 0;
                switch (action.type) {
                    case Card.ACTION_POSITIVE:
                        useActionLayout = R.layout.card_button_positive;
                        break;
                    case Card.ACTION_NEGATIVE:
                        useActionLayout = R.layout.card_button_negative;
                        break;
                }

                action.actionView = inflater.inflate(useActionLayout, actionArea, false);
                Button actionButton = (Button) action.actionView.findViewById(R.id.card_button);

                actionButton.setText(action.label);
                actionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        card.clickListener.onCardClick(action.id, card.tag);
                    }
                });
                actionArea.addView(action.actionView);
            }
        }
    }
}
