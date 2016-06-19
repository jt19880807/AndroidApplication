package com.JTAppStore.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.JTAppStore.R;
import com.JTAppStore.common.AppApplication;
import com.JTAppStore.common.AppConstant;
import com.JTAppStore.ui.base.BaseAppCompatActivity;
import com.JTAppStore.utils.SharedPrenfenceUtil;

/**
 * Created by JiangTao on 2016/6/11.
 */
public class GuideActivity extends BaseAppCompatActivity {
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
}
