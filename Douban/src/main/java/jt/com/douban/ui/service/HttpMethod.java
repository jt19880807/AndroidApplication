package jt.com.douban.ui.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import jt.com.douban.ui.model.ChannelBean;
import jt.com.douban.ui.model.PlayBean;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Devin.Jiang on 2016-07-08.
 */
public class HttpMethod {
    private static final String CHANNEL_URL="http://www.douban.com/";
    private static final String SONG_URL="http://douban.fm/";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit channelRetrofit;
    private Retrofit songRetrofit;
    private DouBanService douBanService;

    private HttpMethod(){
        OkHttpClient.Builder httpClientBuilder=new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        channelRetrofit=new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(CHANNEL_URL)
                .build();
        songRetrofit=new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(SONG_URL)
                .build();
    }

    private static class singlectionHolder{
        private static final HttpMethod INSTANCE=new HttpMethod();
    }

    //获取单例
    public static HttpMethod getInstance() {
        return singlectionHolder.INSTANCE;
    }

    public void getChannels(Subscriber<List<ChannelBean>> subscriber) {
        douBanService=channelRetrofit.create(DouBanService.class);
        douBanService.getChannels()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getSongs(Subscriber<List<PlayBean.SongBean>> subscriber,int channel){
        douBanService=songRetrofit.create(DouBanService.class);
        douBanService.getSongs("n",channel,"mainsite")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    //private class HttpResultFunc<T> implements Func1<HttpResult<T>,T>
}
