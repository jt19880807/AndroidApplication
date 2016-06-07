package com.example.RxjavaAndRetrofit.subscribers;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Devin.Jiang on 2016-06-07.
 */
public class AnimatorOnSubscribe implements Observable.OnSubscribe<Void> {
    final Animator animator;

    public AnimatorOnSubscribe(Animator animator){
        this.animator=animator;
    }
    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        AnimatorListenerAdapter adapter=new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                subscriber.onNext(null);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                subscriber.onCompleted();
            }
        };
        animator.addListener(adapter);
        animator.start();
    }
}
