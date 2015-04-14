package com.twt.service.wenjin.ui.explore.list;

import com.twt.service.wenjin.bean.ExploreItem;

import java.util.ArrayList;

/**
 * Created by WGL on 2015/3/28.
 */
public interface ExploreListView {
    void startRefresh();

    void stopRefresh();

    void toastMessage(String msg);

    void updateListData(ArrayList<ExploreItem> items);

    void addListData(ArrayList<ExploreItem> items);

    void showFooter();

    void hideFooter();
}
