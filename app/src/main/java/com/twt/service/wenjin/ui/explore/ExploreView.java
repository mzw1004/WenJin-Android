package com.twt.service.wenjin.ui.explore;

import com.twt.service.wenjin.bean.ExploreItem;

import java.util.ArrayList;

/**
 * Created by WGL on 2015/3/28.
 */
public interface ExploreView {
    void startRefresh();

    void stopRefresh();

    void toastMessage(String msg);

    void updateListData(ArrayList<ExploreItem> items);
}
