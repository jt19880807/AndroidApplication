package com.JTAppStore.ui.widget;

/**
 * Created by mcdull
 */

public abstract class PagerAdapter extends android.support.v4.view.PagerAdapter {

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    public float getPageSize(int position){
        return getPageWidth(position);
    }
}
