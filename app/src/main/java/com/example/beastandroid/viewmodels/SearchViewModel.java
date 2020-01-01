package com.example.beastandroid.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.beastandroid.di.Resource;
import com.example.beastandroid.di.SearchResource;
import com.example.beastandroid.model.Feed;
import com.example.beastandroid.networking.SearchApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SearchViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";
    private MediatorLiveData<SearchResource<Feed>> mediatorLiveData = new MediatorLiveData<>();
    private SearchApi searchApi;

    @Inject
    public SearchViewModel(SearchApi searchApi){
        this.searchApi = searchApi;
    }

    public void searchQuery(String query){
        cacheData(query);
    }

    public void cacheData(String query){

        mediatorLiveData.setValue(SearchResource.loading((Feed)null));

        final LiveData<SearchResource<Feed>> source = LiveDataReactiveStreams.fromPublisher(
                searchApi.getSearchData("redtube.Videos.searchVideos","json",query)
                        .onErrorReturn(new Function<Throwable, Feed>() {
                            @Override
                            public Feed apply(Throwable throwable) throws Exception {
                                Feed feed = new Feed();
                                feed.setId(-1);
                                return feed;
                            }
                        })
                        .map(new Function<Feed, SearchResource<Feed>>() {
                            @Override
                            public SearchResource<Feed> apply(Feed feed) throws Exception {
                                if (feed.getId() == -1){
                                    return SearchResource.error("Check Internet connection",(Feed)null);
                                }
                                return SearchResource.success(feed);
                            }
                        })
                        .subscribeOn(Schedulers.io())
        );

        mediatorLiveData.addSource(source, new Observer<SearchResource<Feed>>() {
            @Override
            public void onChanged(SearchResource<Feed> feedResource) {
                mediatorLiveData.setValue(feedResource);
                mediatorLiveData.removeSource(source);
            }
        });
    }

    public LiveData<SearchResource<Feed>> observeSearchData(){
        return mediatorLiveData;
    }




}
