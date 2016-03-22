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

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.provider.BaseColumns;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.HashSet;

/**
 * Layout
 */
public class CardStreamLinearLayout extends LinearLayout implements View.OnClickListener {
    public static final String TAG = CardStreamLinearLayout.class.getSimpleName();
    private OnCardClickListener cardClickListener;

    public CardStreamLinearLayout(Context context) {
        super(context);
        setOnHierarchyChangeListener(hierarchyChangeListener);
    }

    public CardStreamLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    public CardStreamLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        for (int i = 0; i < this.getChildCount(); i++) {
            this.getChildAt(i).setVisibility(VISIBLE);
        }
    }

    public void addCard(View cardView) {
        if (cardView.getParent() == null) {
            ViewGroup.LayoutParams param = cardView.getLayoutParams();
            if (param == null) {
                param = generateDefaultLayoutParams();
            }
            super.addView(cardView, 0, param);
        }
    }

    public void removeCard(View cardView) {
        String tag = (String) cardView.getTag();
        this.removeView(cardView);
    }

    private void scrollToCard(String tag) {
        final int count = getChildCount();
        for (int index = 0; index < count; ++index) {
            View child = getChildAt(index);

            if (tag.equals(child.getTag())) {

                ViewParent parent = getParent();
                if( parent != null && parent instanceof ScrollView ){
                    ((ScrollView)parent).smoothScrollTo(
                            0, child.getTop() - getPaddingTop() - child.getPaddingTop());
                }
                return;
            }
        }
    }

    private OnHierarchyChangeListener hierarchyChangeListener = new OnHierarchyChangeListener() {

        @Override
        public void onChildViewAdded(View view, View parent) {
            Log.d(TAG, "Card View added: " + view);
            ViewParent scrollView = parent.getParent();
            if (scrollView != null && scrollView instanceof ScrollView) {
                ((ScrollView) scrollView).fullScroll(FOCUS_UP);
            }
        }

        @Override
        public void onChildViewRemoved(View view, View view1) {
            Log.d(TAG, "Card View removed: " + view);
        }
    };

    public void setOnCardClickListener(OnCardClickListener cardClickListener) {
        this.cardClickListener = cardClickListener;
    }

    @Override
    public void onClick(View view) {
        View cardView = (View) view.getParent().getParent().getParent();
        String tag = (String) cardView.getTag();
        if (tag != null) {
            this.cardClickListener.onCardClick(view.getId(), tag);
            removeCard(cardView);
        }
    }
}
