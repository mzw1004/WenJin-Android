package com.twt.service.wenjin.ui.main;

import com.twt.service.wenjin.bean.NotificationNumInfo;

/**
 * Created by Green on 15-6-22.
 */
public interface OnGetNotificationNumberInfoCallback {

    void onGetNotificationNumberInfoSuccess(NotificationNumInfo notificationNumInfo);

    void onGetNotificationNumberInfoFailed(String argErrorMSG);
}
