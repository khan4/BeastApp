package com.example.beastandroid.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.beastandroid.di.Resource;
import com.example.beastandroid.model.Feed;
import com.example.beastandroid.model.User;
import com.example.beastandroid.model.Video;
import com.example.beastandroid.networking.VideoApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";
    private VideoApi videoApi;
    private MediatorLiveData<Resource<Feed>> mediatorLiveData = new MediatorLiveData<>();

    @Inject
    Retrofit retrofit;

    @Inject
    public MainViewModel(VideoApi videoApi){
        this.videoApi = videoApi;
    }

    public void cacheData(int pageNo){

        mediatorLiveData.setValue(Resource.loading((Feed)null));

        final LiveData<Resource<Feed>> source = LiveDataReactiveStreams.fromPublisher(
                videoApi.getData("redtube.Videos.searchVideos","json",pageNo)
                        .onErrorReturn(new Function<Throwable, Feed>() {
                            @Override
                            public Feed apply(Throwable throwable) throws Exception {

                                Feed feed = new Feed();
                                feed.setId(-1);
                                return feed;
                            }
                        })
                        .map(new Function<Feed, Resource<Feed>>() {
                            @Override
                            public Resource<Feed> apply(Feed feed) throws Exception {
                                if (feed.getId() == -1){
                                    return Resource.error("Check Internet connection",(Feed)null);
                                }
                                return Resource.success(feed);
                            }
                        })
                .subscribeOn(Schedulers.io())
        );

        mediatorLiveData.addSource(source, new Observer<Resource<Feed>>() {
            @Override
            public void onChanged(Resource<Feed> feedResource) {
                mediatorLiveData.setValue(feedResource);
                mediatorLiveData.removeSource(source);
            }
        });
    }

    public LiveData<Resource<Feed>> observeData(){
        return mediatorLiveData;
    }


}
