package com.twt.service.wenjin.ui.search;

import com.twt.service.wenjin.interactor.SearchInteractor;

/**
 * Created by Green on 15/11/12.
 */
public class SearchPresenterImpl implements SearchPresenter, OnGetSearchCallback {

    private SearchView mSearchView;
    private SearchInteractor mSearchInteractor;

    int mPage =1;

    public SearchPresenterImpl(SearchView searchView, SearchInteractor interactor){
        this.mSearchView = searchView;
        this.mSearchInteractor = interactor;
    }


    @Override
    public void OnGetSearchSuccess(Object object) {

    }

    @Override
    public void OnGetSearchFailure(String errorString) {
        mSearchView.toastMessage(errorString);
    }

    @Override
    public void getSearchItems(String keyword) {
    }
}
