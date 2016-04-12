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

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import org.harsurvey.android.util.AnimationHelper;

/**
 * Layout
 */
public class CardStreamLinearLayout extends LinearLayout {
    public static final String TAG = CardStreamLinearLayout.class.getSimpleName();
    private AnimationHelper animationHelper;
    public boolean showInitialAnimation = false;
    private Rect childRect = new Rect();
    private String firstVisibleCardTag = null;
    private boolean layouted = false;

    public CardStreamLinearLayout(Context context) {
        super(context);
        animationHelper = new AnimationHelper(this, null, 0);
    }

    public CardStreamLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        animationHelper = new AnimationHelper(this, attrs, 0);
    }

    @SuppressLint("NewApi")
    public CardStreamLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        animationHelper = new AnimationHelper(this, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        if( changed && !layouted){
            layouted = true;

            ObjectAnimator animator;
            LayoutTransition layoutTransition = new LayoutTransition();

            animator = animationHelper.animators.getDisappearingAnimator(getContext());
            layoutTransition.setAnimator(LayoutTransition.DISAPPEARING, animator);

            animator = animationHelper.animators.getAppearingAnimator(getContext());
            layoutTransition.setAnimator(LayoutTransition.APPEARING, animator);

            layoutTransition.addTransitionListener(animationHelper.transitionListener);

            if( animator != null )
                layoutTransition.setDuration(animator.getDuration());

            setLayoutTransition(layoutTransition);

            if(showInitialAnimation) {
                animationHelper.runInitialAnimations();
            }

            if (firstVisibleCardTag != null) {
                scrollToCard(firstVisibleCardTag);
                firstVisibleCardTag = null;
            }
        }

    }

    /**
     * get the tag of the first visible child in this layout
     *
     * @return tag of the first visible child or null
     */
    public String getFirstVisibleCardTag() {

        final int count = getChildCount();

        if (count == 0)
            return null;

        for (int index = 0; index < count; ++index) {
            //check the position of each view.
            View child = getChildAt(index);
            if (child.getGlobalVisibleRect(childRect) == true)
                return (String) child.getTag();
        }

        return null;
    }

    public void addCard(View cardView, boolean dismiss) {
        if (cardView.getParent() == null) {
            animationHelper.initCard(cardView, dismiss);
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
}
