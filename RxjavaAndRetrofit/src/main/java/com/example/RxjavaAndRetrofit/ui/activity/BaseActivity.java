package com.example.RxjavaAndRetrofit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.RxjavaAndRetrofit.R;

/**
 * Created by Devin.Jiang on 2016-06-06.
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected String TAG=getTAG();
    protected abstract String getTAG();
    protected abstract int getLayoutId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
    }

    protected void openActivity(Class<?> cls,Bundle bundle){
        Intent intent=new Intent(this,cls);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    protected void openActivity(Class<?> cls){
        openActivity(cls,null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
}
