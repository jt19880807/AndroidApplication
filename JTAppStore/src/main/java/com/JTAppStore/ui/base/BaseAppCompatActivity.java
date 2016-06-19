package com.JTAppStore.ui.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.JTAppStore.R;
import com.JTAppStore.common.AppManager;

/**
 * Created by Devin.Jiang on 2016-06-06.
 */
public abstract class BaseAppCompatActivity extends FragmentActivity {
    private String TAG=BaseAppCompatActivity.class.getSimpleName();

    protected abstract int getLayoutId();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addAvtivity(this);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window window=getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if(getLayoutId()!=0){
            setContentView(getLayoutId());
        }
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
    }

    protected void openActivity(Class<?> cls){
        openActivity(cls,null);
    }

    protected void openActivity(Class<?> cls,Bundle bundle){
        Intent intent=new Intent(this,cls);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    protected void openActivityWithOutAnim(Class<?> cls){
        openActivityWithOutAnim(cls,null);
    }

    protected void openActivityWithOutAnim(Class<?> cls,Bundle bundle){
        Intent intent=new Intent(this,cls);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void openActivity(String action){
        openActivity(action,null);
    }

    protected void openActivity(String action,Bundle bundle){
        Intent intent=new Intent(action);
        if(bundle!=null){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
    }
}
