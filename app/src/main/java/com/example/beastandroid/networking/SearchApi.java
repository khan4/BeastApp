package com.example.beastandroid.networking;

import com.example.beastandroid.MainActivity;
import com.example.beastandroid.model.Feed;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryName;

public interface SearchApi {



    @GET("/?")
    Flowable<Feed> getSearchData(
            @Query("data") String data,
            @Query("output") String outPut,
            @Query("search") String search
    );

}
