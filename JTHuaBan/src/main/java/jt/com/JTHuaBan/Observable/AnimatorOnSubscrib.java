package jt.com.JTHuaBan.Observable;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.Log;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by Devin.Jiang on 2016-06-13.
 */
public class AnimatorOnSubscrib implements Observable.OnSubscribe<Void> {
    private static final String TAG=AnimatorOnSubscrib.class.getSimpleName();
    final Animator animator;
    public AnimatorOnSubscrib(Animator animator){
        this.animator=animator;
        Log.d("Log","AnimatorOnSubscrib");
    }

    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        AnimatorListenerAdapter adapter=new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Log.d("Log","onAnimationStart");
                subscriber.onNext(null);
                Log.d("Log","onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.d("Log","onAnimationEnd");
                subscriber.onCompleted();
                Log.d("Log","onAnimationEnd");
            }


        };
        animator.addListener(adapter);
        animator.start();
    }
}
