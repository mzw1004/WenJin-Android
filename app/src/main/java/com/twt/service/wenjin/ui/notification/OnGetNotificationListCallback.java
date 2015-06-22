package com.twt.service.wenjin.ui.notification;

import com.twt.service.wenjin.bean.NotificationItem;

import java.util.List;

/**
 * Created by Green on 15-6-22.
 */
public interface OnGetNotificationListCallback {

    void onGetNotificationListSuccess(List<NotificationItem> itemList);

    void onGetNotificationListFailed(String argErrorMsg);
}
