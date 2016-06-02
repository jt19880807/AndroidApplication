package com.example.RxjavaAndRetrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    private Button btn;
//    private TextView textView;
    private Subscriber<List<MovieModel.SubjectsBean>> subscriber;
    private SubscriberOnNextListener getTopMovieOnNext;
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private List<MovieModel.SubjectsBean> datalist=new ArrayList<>();
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        getMovie(0,10);


    }

    private void initData() {
        datalist=new ArrayList<>();
        MovieModel.SubjectsBean sb=new MovieModel.SubjectsBean();
        sb.setTitle("猫和老鼠");
        sb.setYear("2005");
        datalist.add(sb);

    }

    private void init() {

        btn=(Button)findViewById(R.id.btnId);
        recyclerView= (RecyclerView) findViewById(R.id.datalist);
        movieAdapter=new MovieAdapter(MainActivity.this,datalist);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        getTopMovieOnNext=new SubscriberOnNextListener<List<MovieModel.SubjectsBean>>(){
            @Override
            public void onNext(List<MovieModel.SubjectsBean> subjectsBeen) {
//                Log.d("Log1",subjectsBeen.size()+"");
                //datalist=subjectsBeen;
                datalist.clear();
                datalist.addAll(subjectsBeen);
                movieAdapter.notifyDataSetChanged();

            }
        };
        //movieAdapter=new MovieAdapter(MainActivity.this,datalist);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMovie(10,20);
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
