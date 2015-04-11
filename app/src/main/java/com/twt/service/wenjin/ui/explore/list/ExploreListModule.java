package com.twt.service.wenjin.ui.explore.list;

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
                ExploreListFragment.class
        },
        addsTo = MainModule.class,
        library = true

)

public class ExploreListModule {
    private ExploreListView _exploreListView;

    public ExploreListModule(ExploreListView exploreListView){
        this._exploreListView = exploreListView;
    }

    @Provides @Singleton public ExploreListView provideExploreView(){
        return _exploreListView;
    }

    @Provides @Singleton public ExploreListPresenter provideExplorePresenter(ExploreListView exploreListView,ExploreInteractor exploreInteractor){
        return new ExploreListPresenterImpl(exploreListView,exploreInteractor);
    }
}
