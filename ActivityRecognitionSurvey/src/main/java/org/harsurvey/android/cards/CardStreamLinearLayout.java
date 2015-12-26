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
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Layout
 */
public class CardStreamLinearLayout extends LinearLayout {
    public static final String TAG = CardStreamLinearLayout.class.getSimpleName();
    private ArrayAdapter adapter;
    private List<Card> cards = new ArrayList<>();
    private boolean layouted = false;

    public CardStreamLinearLayout(Context context) {
        super(context);
    }

    public CardStreamLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("NewApi")
    public CardStreamLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setAdapter(ArrayAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!layouted) {
            for (int i = 0; i < adapter.getCount(); i++) {
                addCard(adapter.getView(i, null, this));
            }
            layouted = true;
        }
    }

    public void addCard(View cardView) {
        if (cardView.getParent() == null) {
            ViewGroup.LayoutParams param = cardView.getLayoutParams();
            if (param == null) {
                param = generateDefaultLayoutParams();
            }
            super.addView(cardView, -1, param);
        }
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

}
