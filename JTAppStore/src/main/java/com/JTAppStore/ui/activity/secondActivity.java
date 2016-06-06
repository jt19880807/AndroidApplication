package com.JTAppStore.ui.activity;

import android.os.Bundle;

import com.JTAppStore.R;
import com.JTAppStore.ui.base.BaseAppCompatActivity;

/**
 * Created by Devin.Jiang on 2016-06-06.
 */
public class secondActivity extends BaseAppCompatActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.second_layout;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.second_layout);
    }
}
