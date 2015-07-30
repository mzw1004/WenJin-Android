package com.twt.service.wenjin.ui.notification.readlist;

import com.twt.service.wenjin.bean.NotificationItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Green on 15-6-22.
 */
public interface NotificationView {

    void hideViewMarkAll();

    void startRefresh();

    void stopRefresh();

    void hideLoadMoreFooter();

    void useLoadMoreFooter();


    void toastMessage(String message);

    void refreshItems(List<NotificationItem> items);

    void addMoreItems(List<NotificationItem> items, int argTotalRows);

    void deleteItem(int argPosition);

    void startQuestionArticleActivity(int position);

    void startAnswerActivity(int position);

    void startProfileActivity(int position);
}
