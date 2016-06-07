package com.example.RxjavaAndRetrofit.observable;

import android.animation.Animator;

import com.example.RxjavaAndRetrofit.subscribers.AnimatorOnSubscribe;

import rx.Observable;

/**
 * Created by Devin.Jiang on 2016-06-07.
 */
public class MyObservable  {
    public static Observable<Void> addAnimator(Animator animator){
        return Observable.create(new AnimatorOnSubscribe(animator));
    }
}
