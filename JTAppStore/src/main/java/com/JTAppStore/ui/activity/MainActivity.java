package com.JTAppStore.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.JTAppStore.R;
import com.JTAppStore.ui.base.BaseAppCompatActivity;

public class MainActivity extends BaseAppCompatActivity {

    private Button btn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity(secondActivity.class);
                //openActivityWithOutAnim(secondActivity.class);
            }
        });
    }
}
