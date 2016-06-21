package com.JTAppStore.ui.widget;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Devin.Jiang on 2016-06-21.
 */
public class JTColorAnimationView extends View implements ValueAnimator.AnimatorUpdateListener,
        Animator.AnimatorListener{

    private static final int RED = 0xffFF8080;
    private static final int BLUE = 0xff8080FF;
    private static final int WHITE = 0xffffffff;
    private static final int GREEN = 0xff80ff80;
    private static final int DURATION = 3000;
    ValueAnimator colorAnim=null;
    PageChangeListener pageChangeListener;
    ViewPager.OnPageChangeListener onPageChangeListener;
    public void setOnPageChangeListener( ViewPager.OnPageChangeListener onPageChangeListener){
        this.onPageChangeListener=onPageChangeListener;
    }

    public void setViewPager(ViewPager mViewPager,int count,int... colors){
        if(mViewPager.getAdapter()==null){
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        pageChangeListener.setViewPagerChildCount(count);

    }
    public JTColorAnimationView(Context context) {
        super(context);
    }

    public JTColorAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JTColorAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {

    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {

    }

    private class PageChangeListener implements ViewPager.OnPageChangeListener{

        private int viewPagerChildCount;

        public void setViewPagerChildCount(int viewPagerChildCount) {
            this.viewPagerChildCount=viewPagerChildCount;
        }

        public int getViewPagerChildCount(){
            return viewPagerChildCount;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int count = getViewPagerChildCount() - 1;
            if (count != 0) {
                float length = (position + positionOffset) / count;
                int progress = (int) (length * DURATION);
                JTColorAnimationView.this.seek(progress);
            }
            // call the method by default
            if (onPageChangeListener != null) {
                onPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private void seek(long progress) {
        if (colorAnim==null){
            createDefaultAnimation();
        }
        colorAnim.setCurrentPlayTime(progress);
    }

    private void createDefaultAnimation() {
        colorAnim=ObjectAnimator.ofInt(this,"backgroundColor",WHITE, RED, BLUE, GREEN, WHITE);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setDuration(DURATION);
        colorAnim.addUpdateListener(this);
    }
}
