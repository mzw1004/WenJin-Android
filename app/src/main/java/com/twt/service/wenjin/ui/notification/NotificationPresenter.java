package com.twt.service.wenjin.ui.notification;

import android.view.View;

/**
 * Created by Green on 15-6-22.
 */
public interface NotificationPresenter {

    void firstTimeLoadNotificationItems();

    void refreshNotificationItems();

    void loadMoreNotificationItems();

    void markNotificationAsRead(int argNotifiId);

    void onItemClick(View argView, int argPosition);
}