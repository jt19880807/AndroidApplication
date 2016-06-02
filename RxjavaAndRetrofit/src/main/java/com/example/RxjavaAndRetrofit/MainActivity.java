package com.example.RxjavaAndRetrofit;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.RxjavaAndRetrofit.Model.HttpResult;
import com.example.RxjavaAndRetrofit.Model.MovieModel;
import com.example.RxjavaAndRetrofit.Service.HttpMethod;
import com.example.RxjavaAndRetrofit.Service.MovieService;
import com.example.RxjavaAndRetrofit.adapters.MovieAdapter;
import com.example.RxjavaAndRetrofit.subscribers.ProgressSubscriber;
import com.example.RxjavaAndRetrofit.subscribers.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private Subscriber<List<MovieModel.SubjectsBean>> subscriber;
    private SubscriberOnNextListener getTopMovieOnNext;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private List<MovieModel.SubjectsBean> datalist=new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getMovie(0,10);


    }

    private void init() {
        recyclerView= (RecyclerView) findViewById(R.id.datalist);
        mSwipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        movieAdapter=new MovieAdapter(MainActivity.this,datalist);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        getTopMovieOnNext=new SubscriberOnNextListener<List<MovieModel.SubjectsBean>>(){
            @Override
            public void onNext(List<MovieModel.SubjectsBean> subjectsBeen) {

                if(datalist.size()>0) {

                    Log.d("Log","=="+(datalist.get(0).getId() == subjectsBeen.get(0).getId()));
                    if (datalist.get(0).getId() == subjectsBeen.get(0).getId()) {


                        Toast.makeText(MainActivity.this, "没有最新数据", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("Log","datalist-->"+datalist.get(0).getId());
                        Log.d("Log","subjectsBeen-->"+subjectsBeen.get(0).getId());
                        datalist.clear();
                        datalist.addAll(subjectsBeen);
                        movieAdapter.notifyDataSetChanged();
                    }
                }
                else {
                    datalist.clear();
                    datalist.addAll(subjectsBeen);
                    movieAdapter.notifyDataSetChanged();
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        };

        //设置圆形动画额颜色(最多可设置四个)
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovie(0,10);
            }
        });

    }

    private void getMovie(int start,int count) {
//        String baseUrl="https://api.douban.com/v2/movie/";
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl(baseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .build();
//        MovieService movieService=retrofit.create(MovieService.class);
//        Call<MovieModel> call=movieService.getTopMoview(0,2);
//        call.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                textView.setText(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//                textView.setText(t.getMessage());
//            }
//        });
//        movieService.getTopMoview(0,2)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<MovieModel>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//                    @Override
//                    public void onNext(MovieModel movieModel) {
//                        textView.setText(movieModel.getTitle().toString());
//                    }
//                });

//        subscriber=new Subscriber<List<MovieModel.SubjectsBean>>(){
//
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(List<MovieModel.SubjectsBean> listHttpResult) {
//                textView.setText(listHttpResult.size()+"");
//            }
//        };
//        HttpMethod.getInstance().getTopMovie(subscriber,0,2);
        HttpMethod.getInstance().getTopMovie(
                new ProgressSubscriber(getTopMovieOnNext,MainActivity.this),start,count);
        //Log.d("Log1",movieAdapter.mDataList.size()+"");
    }
}
