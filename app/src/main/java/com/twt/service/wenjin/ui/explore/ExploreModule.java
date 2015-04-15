package com.twt.service.wenjin.ui.explore;

import com.twt.service.wenjin.interactor.ExploreInteractor;
import com.twt.service.wenjin.ui.main.MainModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by WGL on 2015/3/28.
 */
@Module(
        injects = {
                ExploreFragment.class
        },
        addsTo = MainModule.class
)

public class ExploreModule {

    private ExploreView _exploreView;

    public ExploreModule(ExploreView exploreView){
        this._exploreView = exploreView;
    }

    @Provides @Singleton public ExploreView provideExploreView(){
        return _exploreView;
    }

    @Provides @Singleton public ExplorePresenter provideExplorePresenter(ExploreView exploreView, ExploreInteractor exploreInteractor){
        return new ExplorePresenterImpl(exploreView,exploreInteractor);
    }
}
