package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.search.OnGetSearchCallback;

/**
 * Created by Green on 15/11/12.
 */
public interface SearchInteractor {

    void searchContent(String searchinfo,String type, int page,OnGetSearchCallback callback);
}
