package com.twt.service.wenjin.ui.main;

import com.twt.service.wenjin.AppModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/20.
 */
@Module(
        injects = MainActivity.class,
        addsTo = AppModule.class
)
public class MainModule {

    private MainView mMainView;

    public MainModule(MainView mainView) {
        this.mMainView = mainView;
    }

    @Provides
    @Singleton
    public MainView provideMainView() {
        return mMainView;
    }

    @Provides
    @Singleton
    public MainPresenter provideMainPresenter(MainView mainView) {
        return new MainPresenterImpl(mainView);
    }
}
