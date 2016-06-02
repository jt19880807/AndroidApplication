package com.example.RxjavaAndRetrofit.subscribers;

/**
 * Created by Devin.Jiang on 2016-05-27.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
}
