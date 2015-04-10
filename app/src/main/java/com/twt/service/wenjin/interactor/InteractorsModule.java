package com.twt.service.wenjin.interactor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/19.
 */
@Module(
        complete = false,
        library = true
)
public class InteractorsModule {

    @Provides @Singleton
    public LoginInteractor provideLoginInteractor() {
        return new LoginInteractorImpl();
    }

    @Provides @Singleton
    public HomeInteractor provideHomeInteractor() {
        return new HomeInteractorImpl();
    }

    @Provides @Singleton
    public ExploreInteractor provideExploreInteractor(){return new ExploreInteractorImpl();}
}
