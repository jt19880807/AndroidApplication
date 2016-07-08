package com.example.RxjavaAndRetrofit.Service;

import com.example.RxjavaAndRetrofit.Model.HttpResult;
import com.example.RxjavaAndRetrofit.Model.MovieModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okio.Timeout;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Devin.Jiang on 2016-05-26.
 */
public class HttpMethod {
    private static final String BASE_URL="https://api.douban.com/v2/movie/";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private MovieService movieService;

    private HttpMethod(){
        OkHttpClient.Builder httpClientBuilder=new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit=new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        movieService=retrofit.create(MovieService.class);
    }

    //在访问singlectionHolder创建单例
    private static class singlectionHolder{
        private static final HttpMethod INSTANCE=new HttpMethod();
    }

    //获取单例
    public static HttpMethod getInstance(){
        return singlectionHolder.INSTANCE;
    }

    public void getTopMovie(Subscriber<List<MovieModel.SubjectsBean>> subscriber,int start,int count){
        movieService.getTopMovie(start,count)
                .map(new HttpResultFunc<List<MovieModel.SubjectsBean>>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    /***
     * 统一处理http的resultCode，并将HttpResult的Data部分剥离出来返回给subscriber
     * @param <T>
     */
    private class HttpResultFunc<T> implements Func1<HttpResult<T>,T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            if(httpResult.getCount()==0) {
                throw new ApiException(100);
            }
            return httpResult.getSubjects();
        }
    }
}
