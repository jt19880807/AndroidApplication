package jt.com.JTHuaBan.Observable;

import android.animation.Animator;
import android.util.Log;

import rx.Observable;

/**
 * Created by Devin.Jiang on 2016-06-13.
 */
public class WelcomeObservable {
    public static Observable<Void> addAnimator(Animator animator){
        Log.d("Log","addAnimator");
        return Observable.create(new AnimatorOnSubscrib(animator));
    }
}
