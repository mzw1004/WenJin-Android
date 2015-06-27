package com.twt.service.wenjin.ui.notification.readlist;

import android.view.View;

/**
 * Created by Green on 15-6-22.
 */
public interface NotificationPresenter {

    void firstTimeLoadNotificationItems(int type);

    void refreshNotificationItems(int type);

    void loadMoreNotificationItems(int type);

    void markNotificationAsRead(int argNotifiId);

    void onItemClick(View argView, int argPosition);
}
