package com.twt.service.wenjin.ui.search;


/**
 * Created by Green on 15/11/12.
 */
public interface OnGetSearchCallback {
    void OnGetSearchSuccess(Object object);

    void OnGetSearchFailure(String errorString);
}
