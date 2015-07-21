package com.twt.service.wenjin.ui.notification.readlist;

import com.twt.service.wenjin.bean.NotificationItem;
import com.twt.service.wenjin.bean.NotificationResponse;

import java.util.List;

/**
 * Created by Green on 15-6-22.
 */
public interface OnGetNotificationListCallback {

    void onGetNotificationListSuccess(NotificationResponse argResponse);

    void onGetNotificationListFailed(String argErrorMsg);
}
