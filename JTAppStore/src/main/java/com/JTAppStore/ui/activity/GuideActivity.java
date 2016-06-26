package com.JTAppStore.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.ViewGroup;

import com.JTAppStore.R;
import com.JTAppStore.common.AppApplication;
import com.JTAppStore.common.AppConstant;
import com.JTAppStore.ui.base.BaseAppCompatActivity;
import com.JTAppStore.ui.widget.JTColorAnimationView;
import com.JTAppStore.ui.widget.PagerAdapter;
import com.JTAppStore.ui.widget.ViewPager;
import com.JTAppStore.utils.SharedPrenfenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JiangTao on 2016/6/11.
 */
public class GuideActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mPager;
    private List<View> pagers=new ArrayList<>();
    private VerticalFragementPagerAdapter mAdapter;
    private JTColorAnimationView colorAnimationView;
    @Override
    protected int getLayoutId() {
        return R.layout.guide_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPrenfenceUtil.putString(this, AppConstant.GUIDE_SHOW,
                AppApplication.getAppContext().getVersion());

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private class VerticalFragementPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return pagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pagers.get(position));
            return pagers.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(pagers.get(position));
        }
    }
}
