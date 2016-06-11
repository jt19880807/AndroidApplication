package com.JTAppStore.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.JTAppStore.common.AppApplication;
import com.JTAppStore.common.AppConstant;
import com.JTAppStore.ui.base.BaseAppCompatActivity;
import com.JTAppStore.utils.SharedPrenfenceUtil;

/**
 * Created by JiangTao on 2016/6/11.
 */
public class EnterActivity extends BaseAppCompatActivity {
    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isFinishing()){
            return;
        }
        String versionShared= SharedPrenfenceUtil.getString(this, AppConstant.GUIDE_SHOW,"");
        String versionName= AppApplication.getAppContext().getVersion();
        if (TextUtils.isEmpty(versionShared)||!versionShared.equalsIgnoreCase(versionName)){
            //显示引导页
            openActivity(GuideActivity.class);
        }
        else {
            //显示主页
            openActivity(MainActivity.class);
        }
        finish();
    }
}
