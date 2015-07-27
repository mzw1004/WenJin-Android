package com.twt.service.wenjin.ui.explore.list;

import com.twt.service.wenjin.bean.ExploreItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WGL on 2015/3/28.
 */
public interface ExploreListView {
    void startRefresh();

    void stopRefresh();

    void toastMessage(String msg);

    void updateListData(List<ExploreItem> items);

    void addListData(List<ExploreItem> items);

    void startQuestionArticlActivity(int position);

    void startProfileActivity(int position);

    void startAnswerActivity(int position);

    void showFooter();

    void hideFooter();
}
