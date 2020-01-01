package com.example.beastandroid.di;

import androidx.lifecycle.ViewModel;

import com.example.beastandroid.viewmodels.MainViewModel;
import com.example.beastandroid.viewmodels.SearchViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    public abstract ViewModel bindprofileViewModel(MainViewModel mainViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    public abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);
}
