package com.example.RxjavaAndRetrofit.subscribers;

import android.content.Context;
import android.widget.Toast;

import com.example.RxjavaAndRetrofit.progress.ProgressCancelListener;
import com.example.RxjavaAndRetrofit.progress.ProgressDialogHandler;

import rx.Subscriber;

/**
 * Created by Devin.Jiang on 2016-05-27.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{
    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context context;
    private ProgressDialogHandler progressDialogHandler;
    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener,Context context){
        this.mSubscriberOnNextListener=mSubscriberOnNextListener;
        this.context=context;
        progressDialogHandler=new ProgressDialogHandler(context,this,true);
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(T t) {
        mSubscriberOnNextListener.onNext(t);
    }

    @Override
    public void onCancelProgress() {
        if(!this.isUnsubscribed())
            this.unsubscribe();
    }

    private void showProgressDialog() {
        if(progressDialogHandler!=null)
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
    }

    private void dismissProgressDialog(){
        if (progressDialogHandler != null) {
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            progressDialogHandler = null;
        }
    }

}
