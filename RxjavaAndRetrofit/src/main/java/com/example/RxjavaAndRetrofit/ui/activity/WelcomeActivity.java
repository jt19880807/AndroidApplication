package com.example.RxjavaAndRetrofit.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.example.RxjavaAndRetrofit.MainActivity;
import com.example.RxjavaAndRetrofit.R;
import com.example.RxjavaAndRetrofit.observable.MyObservable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Devin.Jiang on 2016-06-06.
 */
public class WelcomeActivity extends BaseActivity {
    private ImageView imageView;
    private MyHandler myHandler;
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
        imageView=(ImageView)findViewById(R.id.img_welcome);
        myHandler=new MyHandler(imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //myHandler.sendEmptyMessageDelayed(1,3000);
        Animator animator= AnimatorInflater.loadAnimator(WelcomeActivity.this,R.animator.welcome_animator);
        animator.setTarget(imageView);
        MyObservable.addAnimator(animator)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
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

    public class MyHandler extends Handler{

        ImageView imageView;
        public MyHandler(ImageView imageView){
            this.imageView=imageView;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Animator animator= AnimatorInflater.loadAnimator(WelcomeActivity.this,R.animator.welcome_animator);
            animator.setTarget(imageView);
            animator.start();
            openActivity(MainActivity.class);
            finish();
        }
    }
}
