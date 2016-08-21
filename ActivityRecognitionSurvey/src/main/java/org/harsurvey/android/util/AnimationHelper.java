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

package org.harsurvey.android.util;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ScrollView;

import org.harsurvey.android.cards.CardStreamAnimator;
import org.harsurvey.android.cards.CardStreamLinearLayout;
import org.harsurvey.android.cards.DefaultCardStreamAnimator;
import org.harsurvey.android.survey.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Animation Utilities Helper
 */
public class AnimationHelper {
    public static final String TAG = AnimationHelper.class.getSimpleName();

    public static final int ANIMATION_SPEED_SLOW = 1001;
    public static final int ANIMATION_SPEED_NORMAL = 1002;
    public static final int ANIMATION_SPEED_FAST = 1003;

    private CardStreamLinearLayout context;
    public CardStreamAnimator animators;
    private List<View> fixedViewList = new ArrayList<View>();

    public AnimationHelper(CardStreamLinearLayout context, AttributeSet attrs, int defStyle) {
        this.context = context;
        initialize(attrs, defStyle);
    }

    private boolean swiping;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void initialize(AttributeSet attrs, int defStyle) {

        float speedFactor = 1.f;

        if (attrs != null) {
            TypedArray a = context.getContext().obtainStyledAttributes(attrs,
                    R.styleable.CardStream, defStyle, 0);

            if( a != null ){
                int speedType = a.getInt(R.styleable.CardStream_animationDuration, 1001);
                switch (speedType){
                    case ANIMATION_SPEED_FAST:
                        speedFactor = 0.5f;
                        break;
                    case ANIMATION_SPEED_NORMAL:
                        speedFactor = 1.f;
                        break;
                    case ANIMATION_SPEED_SLOW:
                        speedFactor = 2.f;
                        break;
                }

                String animatorName = a.getString(R.styleable.CardStream_animators);

                try {
                    if( animatorName != null )
                        animators = (CardStreamAnimator) getClass().getClassLoader()
                                .loadClass(animatorName).newInstance();
                } catch (Exception e) {
                    Log.e(TAG, "Fail to load animator:" + animatorName, e);
                } finally {
                    if(animators == null)
                        animators = new DefaultCardStreamAnimator();
                }
                a.recycle();
            }
        }

        animators.setSpeedFactor(speedFactor);
        mSwipeSlop = ViewConfiguration.get(context.getContext()).getScaledTouchSlop();
        context.setOnHierarchyChangeListener(hierarchyChangeListener);
    }

    public void initCard(View cardView, boolean canDismiss) {
        resetAnimatedView(cardView);
        cardView.setOnTouchListener(touchListener);
        if (!canDismiss)
            fixedViewList.add(cardView);
    }

    /**
     * Handle touch events to fade/move dragged items as they are swiped out
     */
    private View.OnTouchListener touchListener = new View.OnTouchListener() {

        private float dowX;
        private float downY;

        @Override
        public boolean onTouch(final View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dowX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    resetAnimatedView(v);
                    swiping = false;
                    dowX = 0.f;
                    downY = 0.f;
                    break;
                case MotionEvent.ACTION_MOVE: {

                    float x = event.getX() + v.getTranslationX();
                    float y = event.getY() + v.getTranslationY();

                    dowX = dowX == 0.f ? x : dowX;
                    downY = downY == 0.f ? x : downY;

                    float deltaX = x - dowX;
                    float deltaY = y - downY;

                    if (!swiping && isSwiping(deltaX, deltaY)) {
                        swiping = true;
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    } else {
                        swipeView(v, deltaX, deltaY);
                    }
                }
                break;
                case MotionEvent.ACTION_UP: {
                    // User let go - figure out whether to animate the view out, or back into place
                    if (swiping) {
                        float x = event.getX() + v.getTranslationX();
                        float y = event.getY() + v.getTranslationY();

                        float deltaX = x - dowX;
                        float deltaY = y - dowX;
                        float deltaXAbs = Math.abs(deltaX);

                        // User let go - figure out whether to animate the view out, or back into place
                        boolean remove = deltaXAbs > v.getWidth() / 4 && !isFixedView(v);
                        if( remove )
                            handleViewSwipingOut(v, deltaX, deltaY);
                        else
                            handleViewSwipingIn(v, deltaX, deltaY);
                    }
                    dowX = 0.f;
                    downY = 0.f;
                    swiping = false;
                }
                break;
                default:
                    return false;
            }
            return false;
        }
    };

    /**
     * Check whether a user moved enough distance to start a swipe action or not.
     *
     * @param deltaX
     * @param deltaY
     * @return true if a user is swiping.
     */
    protected boolean isSwiping(float deltaX, float deltaY) {

        if (mSwipeSlop < 0) {
            //get swipping slop from ViewConfiguration;
            mSwipeSlop = ViewConfiguration.get(context.getContext()).getScaledTouchSlop();
        }

        boolean swipping = false;
        float absDeltaX = Math.abs(deltaX);

        if( absDeltaX > mSwipeSlop )
            return true;

        return swipping;
    }

    private boolean isFixedView(View v) {;
        return fixedViewList.contains(v);
    }

    /**
     * Swipe a view by moving distance
     *
     * @param child a target view
     * @param deltaX x moving distance by x-axis.
     * @param deltaY y moving distance by y-axis.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    protected void swipeView(View child, float deltaX, float deltaY) {
        if (isFixedView(child)){
            deltaX = deltaX / 4;
        }

        float deltaXAbs = Math.abs(deltaX);
        float fractionCovered = deltaXAbs / (float) child.getWidth();

        child.setTranslationX(deltaX);
        child.setAlpha(1.f - fractionCovered);

        if (deltaX > 0)
            child.setRotationY(-15.f * fractionCovered);
        else
            child.setRotationY(15.f * fractionCovered);
    }

    private void resetAnimatedView(View child) {
        child.setAlpha(1.f);
        child.setTranslationX(0.f);
        child.setTranslationY(0.f);
        child.setRotation(0.f);
        child.setRotationY(0.f);
        child.setRotationX(0.f);
        child.setScaleX(1.f);
        child.setScaleY(1.f);
    }

    private int mSwipeSlop = -1;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void runInitialAnimations() {
        if( animators == null )
            return;

        final int count = context.getChildCount();

        for (int index = 0; index < count; ++index) {
            final View child = context.getChildAt(index);
            ObjectAnimator animator =  animators.getInitalAnimator(context.getContext());
            if( animator != null ){
                animator.setTarget(child);
                animator.start();
            }
        }
    }

    private void handleViewSwipingOut(final View child, float deltaX, float deltaY) {
        ObjectAnimator animator = animators.getSwipeOutAnimator(child, deltaX, deltaY);
        if( animator != null ){
            animator.addListener(new EndAnimationWrapper() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    context.removeCard(child);
                }
            });
        } else {
            context.removeCard(child);
        }

        if( animator != null ){
            animator.setTarget(child);
            animator.start();
        }
    }

    private void handleViewSwipingIn(final View child, float deltaX, float deltaY) {
        ObjectAnimator animator = animators.getSwipeInAnimator(child, deltaX, deltaY);
        if( animator != null ){
            animator.addListener(new EndAnimationWrapper() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    child.setTranslationY(0.f);
                    child.setTranslationX(0.f);
                }
            });
        } else {
            child.setTranslationY(0.f);
            child.setTranslationX(0.f);
        }

        if( animator != null ){
            animator.setTarget(child);
            animator.start();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setCardStreamAnimator( CardStreamAnimator animators ){

        if( animators == null )
            animators = new CardStreamAnimator.EmptyAnimator();
        else
            this.animators = animators;

        LayoutTransition layoutTransition = context.getLayoutTransition();

        if( layoutTransition != null ){
            layoutTransition.setAnimator(LayoutTransition.APPEARING,
                    animators.getAppearingAnimator(context.getContext()));
            layoutTransition.setAnimator(LayoutTransition.DISAPPEARING,
                    animators.getDisappearingAnimator(context.getContext()));
        }
    }
    /**
     * Handle end-transition animation event of each child and launch a following animation.
     */

    public LayoutTransition.TransitionListener transitionListener
            = new LayoutTransition.TransitionListener() {

        @Override
        public void startTransition(LayoutTransition transition, ViewGroup container, View
                view, int transitionType) {
            Log.v(TAG, "Start LayoutTransition animation:" + transitionType);
        }

        @Override
        public void endTransition(LayoutTransition transition, ViewGroup container,
                                  final View view, int transitionType) {

            Log.v(TAG, "End LayoutTransition animation:" + transitionType);
            if (transitionType == LayoutTransition.APPEARING) {
                final View area = view.findViewById(R.id.card_actionarea);
                if (area != null) {
                    runShowActionAreaAnimation(container, area);
                }
            }
        }
    };

    private void runShowActionAreaAnimation(View parent, View area) {
        area.setPivotY(0.f);
        area.setPivotX(parent.getWidth() / 2.f);

        area.setAlpha(0.5f);
        area.setRotationX(-90.f);
        area.animate().rotationX(0.f).alpha(1.f).setDuration(400);
    }

    private ViewGroup.OnHierarchyChangeListener hierarchyChangeListener =
            new ViewGroup.OnHierarchyChangeListener() {

        @Override
        public void onChildViewAdded(final View parent, final View child) {
            Log.v(TAG, "Card View added: " + child);
            ViewParent scrollView = parent.getParent();
            if (scrollView != null && scrollView instanceof ScrollView) {
                ((ScrollView) scrollView).fullScroll(View.FOCUS_UP);
            }

            if (context.getLayoutTransition() != null) {
                View view = child.findViewById(R.id.card_actionarea);
                if (view != null)
                    view.setAlpha(0.f);
            }
        }

        @Override
        public void onChildViewRemoved(View parent, View child) {
            Log.v(TAG, "Card View removed: " + child);
            fixedViewList.remove(child);
        }
    };

    public static void reveal(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            int cx = view.getWidth() / 2;
            int cy = view.getHeight() / 2;

            // get the final radius for the clipping circle
            float finalRadius = (float) Math.hypot(cx, cy);

            // create the animator for this view (the start radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);

            // make the view visible and start the animation
            view.setVisibility(View.VISIBLE);
            anim.start();
        }
        else {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Empty default AnimationListener
     */
    private abstract class EndAnimationWrapper implements Animator.AnimatorListener {

        @Override
        public void onAnimationStart(Animator animation) {
        }

        @Override
        public void onAnimationCancel(Animator animation) {
        }

        @Override
        public void onAnimationRepeat(Animator animation) {
        }
    }//end of inner class
}
