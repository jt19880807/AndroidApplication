package com.example.RxjavaAndRetrofit.Service;

import com.example.RxjavaAndRetrofit.Model.HttpResult;
import com.example.RxjavaAndRetrofit.Model.MovieModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.observers.Observers;

/**
 * Created by Devin.Jiang on 2016-05-26.
 */
public interface MovieService {
    @GET("top250")
    Observable<HttpResult<List<MovieModel.SubjectsBean>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
