package com.twt.service.wenjin.ui.home;

import com.twt.service.wenjin.ui.main.MainModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/22.
 */
@Module(
        injects = {
                HomeFragment.class
        },
        addsTo = MainModule.class,
        library = true
)
public class HomeModule {

    private HomeView mHomeView;

    public HomeModule(HomeView homeView) {
        this.mHomeView = homeView;
    }

    @Provides
    @Singleton
    public HomeView provideHomeView() {
        return mHomeView;
    }

    @Provides
    @Singleton
    public HomePresenter provideHomePresenter(HomeView homeView) {
        return new HomePresenterImpl(homeView);
    }

}
