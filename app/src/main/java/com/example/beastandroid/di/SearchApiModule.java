package com.example.beastandroid.di;

import com.example.beastandroid.networking.SearchApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class SearchApiModule {

    @Provides
    static SearchApi provideSearchApi(Retrofit retrofit){
        return retrofit.create(SearchApi.class);
    }

}
