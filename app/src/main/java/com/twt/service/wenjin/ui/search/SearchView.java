package com.twt.service.wenjin.ui.search;

import com.twt.service.wenjin.bean.SearchResponse;

/**
 * Created by Green on 15/11/12.
 */
public interface SearchView {
    void bindSearchResultsToView(SearchResponse response);

    void toastMessage(String msg);
}
