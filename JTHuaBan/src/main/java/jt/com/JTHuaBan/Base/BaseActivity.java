package jt.com.JTHuaBan.Base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by JiangTao on 2016/6/11.
 */
public abstract  class BaseActivity extends AppCompatActivity {
    protected String TAG=getTAG();
    protected abstract String getTAG();
    protected abstract int getLayoutId();
    protected Context mContext;
    //是否登陆
    public boolean isLogin=false;
    //授权
    public String mAuthorization;
    /**
     * 所有的subcription可以添加到CompositeSubcription中，
     * 然后调用CompositeSubcription.unsubcribe()方法在同一时间退订
     * @return
     */
    private CompositeSubscription mCompositeSubscription;
    public CompositeSubscription getCompositeSubscription(){
        if (this.mCompositeSubscription==null){
            this.mCompositeSubscription=new CompositeSubscription();
        }
        return mCompositeSubscription;
    }

    public void addSubscription(Subscription s){
        if (s==null){
            return;
        }
        if (this.mCompositeSubscription==null){
            this.mCompositeSubscription=new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            Window window=getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(getLayoutId());
    }
}
