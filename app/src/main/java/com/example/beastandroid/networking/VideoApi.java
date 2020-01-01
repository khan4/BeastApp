package com.example.beastandroid.networking;

import com.example.beastandroid.di.Resource;
import com.example.beastandroid.model.Feed;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface VideoApi {

    @GET("/?")
    Flowable<Feed> getData(
            @Query("data") String data,
            @Query("output") String output,
            @Query("page") int page
    );
}
