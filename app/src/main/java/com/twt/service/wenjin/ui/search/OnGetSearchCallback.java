package com.twt.service.wenjin.ui.search;

import com.twt.service.wenjin.bean.SearchResponse;

/**
 * Created by Green on 15/11/12.
 */
public interface OnGetSearchCallback {
    void OnGetSearchSuccess(SearchResponse response);

    void OnGetSearchFailure(String errorString);
}
