package com.example.beastandroid.factory;

import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindsViewModelFactory(ViewModelProviderFactory providerFactory);

   // @Provides
   // static ViewModelProvider.Factory bindsViewModelFactory(ViewModelProviderFactory factory){
   //     return factory;
    //}


}
