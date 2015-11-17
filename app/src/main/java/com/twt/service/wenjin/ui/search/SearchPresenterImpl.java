package com.twt.service.wenjin.ui.search;

/**
 * Created by Green on 15/11/12.
 */
public class SearchPresenterImpl implements SearchPresenter, OnGetSearchCallback {

    private SearchView mSearchView;

    public SearchPresenterImpl(SearchView searchView){
        this.mSearchView = searchView;
    }

    @Override
    public void OnGetSearchSuccess() {

    }

    @Override
    public void OnGetSearchFailure(String errorString) {

    }
}
