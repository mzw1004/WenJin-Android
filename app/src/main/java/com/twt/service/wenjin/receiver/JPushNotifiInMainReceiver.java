package com.twt.service.wenjin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.notification.NotificationFragment;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Green on 15-6-24.
 */
public class JPushNotifiInMainReceiver extends BroadcastReceiver{

    private static final String LOG_TAG = JPushNotifiInMainReceiver.class.getSimpleName();

    private static IntentFilter mIntentFilter = null;

    NotificationFragment.IUpdateNotificationIcon iUpdateNotificationIcon;

    public JPushNotifiInMainReceiver(NotificationFragment.IUpdateNotificationIcon argUpdateNotificationIcon){
        this.iUpdateNotificationIcon = argUpdateNotificationIcon;
    }

    public static IntentFilter getIntentFilterInstance(){
        if(mIntentFilter == null){
            mIntentFilter = new IntentFilter();
            mIntentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
            mIntentFilter.addAction("cn.jpush.android.intent.NOTIFICATION_RECEIVED");
            mIntentFilter.addCategory("com.twt.service.wenjin");
        }
        return mIntentFilter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LogHelper.v(LOG_TAG, intent.getAction());
        if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())){
            LogHelper.v(LOG_TAG, "update notification icon in JPushNotifiInMainReceiver Class");
            iUpdateNotificationIcon.updateNotificationIcon();
        }
    }
}
