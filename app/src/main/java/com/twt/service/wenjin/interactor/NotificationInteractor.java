package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.main.OnGetNotificationNumberInfoCallback;
import com.twt.service.wenjin.ui.notification.readlist.OnGetNotificationListCallback;

/**
 * Created by Green on 15-6-22.
 */
public interface NotificationInteractor {

    void getNotificationNumberInfo(long argTimestampNow,OnGetNotificationNumberInfoCallback callback);

    void getNotificationList(int argPage, int type, OnGetNotificationListCallback callback);

    void setNotificationMarkasread(int argNotificationId);
}
