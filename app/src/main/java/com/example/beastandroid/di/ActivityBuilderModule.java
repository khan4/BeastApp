package com.example.beastandroid.di;

import com.example.beastandroid.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilderModule {

    @ContributesAndroidInjector(
            modules = {MainViewModelModule.class,VideoApiModule.class,SearchApiModule.class}
    )
    abstract MainActivity contributeMainActivity();

}
