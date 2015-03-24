package com.twt.service.wenjin.ui.home;

import com.twt.service.wenjin.bean.HomeItem;

import java.util.ArrayList;

/**
 * Created by M on 2015/3/22.
 */
public interface HomeView {

    void startRefresh();

    void stopRefresh();

    void toastMessage(String message);

    void updateListData(ArrayList<HomeItem> items);
}
