package com.twt.service.wenjin.support;

import android.app.Notification;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.twt.service.wenjin.R;

import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by Green on 15-6-7.
 */
public class JPushHelper {

    public final static String LOG_TAG = JPushHelper.class.getSimpleName();

    public static Context mContext;

    private static final int MSG_SET_ALIAS = 1001;
    private static final int MSG_SET_TAGS = 1002;

    private static final int MSG_CANCEL_ALIAS = 1003;


    private String mAlias;
    private Set<String> mTags;

    public JPushHelper(String alias, Set<String> tags){
        mAlias = alias;
        mTags = tags;

    }

    private final android.os.Handler mHandler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case MSG_SET_ALIAS:
                    LogHelper.v(LOG_TAG, "set alias in handler");
                    JPushInterface.setAliasAndTags(mContext, (String) msg.obj, null, mAliaCallback);
                    break;
                case MSG_CANCEL_ALIAS:
                    LogHelper.v(LOG_TAG,"cancel alias in handler");
                    JPushInterface.setAliasAndTags(mContext, "", null, mAliaCallback);
                    break;
                case MSG_SET_TAGS:
                    LogHelper.v(LOG_TAG,"set tags in handler");
                    JPushInterface.setAliasAndTags(mContext, null, (Set<String>) msg.obj, mTagsCallback);
                    break;

                default:
                    LogHelper.v(LOG_TAG,"unhandled msg - " + msg.what);
            }
        }
    };

    private final TagAliasCallback mAliaCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code){
                case 0:
                    LogHelper.v(LOG_TAG,"Set or Cancel alias and tag success");
                    break;

                case 6002:
                    LogHelper.v(LOG_TAG, "Failed to set alias and tags due to timeout. Try again after 60S.");

                    if(NetworkHelper.isOnline()){
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    }else {
                        LogHelper.v(LOG_TAG, "No network");
                    }
                    break;

                default:
                    LogHelper.v(LOG_TAG, "Failed with errorCode = " + code);

            }
        }
    };

    private final TagAliasCallback mTagsCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            switch (code){
                case 0:
                    LogHelper.v(LOG_TAG, "Set tags and alias success");
                    break;

                case 6002:
                    LogHelper.v(LOG_TAG, "Failed to set alias and tags due to timeout, Try again after 60S");
                    if(NetworkHelper.isOnline()){
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
                    }
                    break;

                default:
            }
        }
    };


    public void setTags(){
        if( mTags == null || mTags.isEmpty()){
            LogHelper.v(LOG_TAG, "tags不能为空");
        }

        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, mTags));

    }

    public void setAlias(){
        if(TextUtils.isEmpty(mAlias)){
            LogHelper.v(LOG_TAG, "alias不能为空");
            return;
        }
        if(!StrMatchHelper.isDigtial0Chinese0English(mAlias)){
            LogHelper.v(LOG_TAG, "alias格式不正确");
            return;
        }

        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, mAlias));
    }

    public void cancelAlias(){
        mHandler.sendMessage(mHandler.obtainMessage(MSG_CANCEL_ALIAS, ""));
    }

    public static void setNotiStyleBasic(){
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(mContext);
        builder.statusBarDrawable = R.mipmap.ic_launcher;
        builder.notificationFlags = Notification.FLAG_AUTO_CANCEL;
        builder.notificationDefaults = Notification.DEFAULT_SOUND;
        JPushInterface.setPushNotificationBuilder(1, builder);
    }

}
