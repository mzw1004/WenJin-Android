package com.twt.service.wenjin.ui.search;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.SearchInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Green on 15/11/12.
 */
@Module(
        injects = SearchActivity.class,
        addsTo = AppModule.class
)
public class SearchModule {

    private SearchView mSearchView;

    public SearchModule(SearchView searchView){
        this.mSearchView = searchView;
    }

    @Provides
    @Singleton
    public SearchView provideSearchView(){
        return mSearchView;
    }

    @Provides
    @Singleton
    public SearchPresenter provideSearchPresenter(SearchView searchView, SearchInteractor interactor){
        return new SearchPresenterImpl(searchView, interactor);
    }


}
