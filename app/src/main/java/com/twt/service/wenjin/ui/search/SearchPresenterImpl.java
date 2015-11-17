package com.twt.service.wenjin.ui.search;

import com.twt.service.wenjin.interactor.SearchInteractor;

/**
 * Created by Green on 15/11/12.
 */
public class SearchPresenterImpl implements SearchPresenter, OnGetSearchCallback {

    private SearchView mSearchView;
    private SearchInteractor mSearchInteractor;

    private int mPage =0;

    public SearchPresenterImpl(SearchView searchView){
        this.mSearchView = searchView;
    }

    @Override
    public void OnGetSearchSuccess() {

    }

    @Override
    public void OnGetSearchFailure(String errorString) {

    }

    @Override
    public void getSearchItems(String keyword) {
        mSearchInteractor.searchContent(keyword,mPage,this);
    }
}
