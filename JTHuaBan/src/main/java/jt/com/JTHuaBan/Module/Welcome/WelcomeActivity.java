package jt.com.JTHuaBan.Module.Welcome;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import jt.com.JTHuaBan.Base.BaseActivity;
import jt.com.JTHuaBan.Module.Main.MainActivity;
import jt.com.JTHuaBan.Observable.WelcomeObservable;
import jt.com.JTHuaBan.R;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by JiangTao on 2016/6/11.
 */
public class WelcomeActivity extends BaseActivity {
//    @Bind(R.id.img_welcome)
//    ImageView img_welcome;
    private ImageView img_welcome;
    @Override
    protected String getTAG() {
        return WelcomeActivity.class.getSimpleName();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        img_welcome=(ImageView)findViewById(R.id.img_welcome);
        //ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //openActivity(MainActivity.class);
        Animator animator= AnimatorInflater.loadAnimator(mContext,R.animator.welcome_animator);
        animator.setTarget(img_welcome);
        //animator.start();
        WelcomeObservable.addAnimator(animator)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Log","onCompleted");
                        openActivity(MainActivity.class);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Void aVoid) {

                    }
                });
    }
}
