package com.twt.service.wenjin.ui.home;

import com.twt.service.wenjin.bean.HomeItem;

import java.util.ArrayList;

/**
 * Created by M on 2015/3/22.
 */
public interface HomeView {

    void showRefresh();

    void hideRefresh();

    void hideLoadMoreFooter();

    void useLoadMoreFooter();

    void showFabMenu();

    void hideFabMenu();

    void toastMessage(String message);

    void refreshItems(ArrayList<HomeItem> items);

    void loadMoreItems(ArrayList<HomeItem> items);

    void startQuestionArticlActivity(int position);

    void startAnswerActivity(int position);

    void startProfileActivity(int position);

}
