package com.example.beastandroid.di;

import com.example.beastandroid.networking.VideoApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class VideoApiModule {

    @Provides
    static VideoApi providesVideoApi(Retrofit retrofit){
        return retrofit.create(VideoApi.class);
    }

}
