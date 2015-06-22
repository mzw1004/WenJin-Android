package com.twt.service.wenjin.ui.notification;

import com.twt.service.wenjin.bean.NotificationItem;

import java.util.ArrayList;

/**
 * Created by Green on 15-6-22.
 */
public interface NotificationView {

    void showRefresh();

    void hideRefresh();

    void hideLoadMoreFooter();

    void useLoadMoreFooter();


    void toastMessage(String message);

    void refreshItems(ArrayList<NotificationItem> items);

    void loadMoreItems(ArrayList<NotificationItem> items);

    void startQuestionActivity(int position);

    void startAnswerActivity(int position);

    void startProfileActivity(int position);
}
