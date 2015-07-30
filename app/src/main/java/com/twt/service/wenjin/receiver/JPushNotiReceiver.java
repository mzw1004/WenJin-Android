package com.twt.service.wenjin.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.twt.service.wenjin.WenJinApp;
import com.twt.service.wenjin.bean.Article;
import com.twt.service.wenjin.bean.NotificationItem;
import com.twt.service.wenjin.bean.NotificationMsg;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailActivity;
import com.twt.service.wenjin.ui.article.ArticleActivity;
import com.twt.service.wenjin.ui.main.MainActivity;
import com.twt.service.wenjin.ui.notification.NotificationType;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;
import com.twt.service.wenjin.ui.welcome.WelcomeActivity;

import java.util.Iterator;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Green on 15-6-7.
 */
public class JPushNotiReceiver extends BroadcastReceiver {

    private static final String LOG_TAG  = JPushNotiReceiver.class.getSimpleName();
    private static final String TAG = "JPush";


    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogHelper.v(LOG_TAG, "[JPushNotiReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if(JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())){
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            LogHelper.v(LOG_TAG, "[JPushNotiReceiver] 接受Registration Id: " + regId);

        }else if(JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())){
            LogHelper.v(LOG_TAG, "[JPushNotiReceiver] 接收到推送下来的自定义消息:" + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            processCustomMessage(context, bundle);
        }else if(JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogHelper.v(LOG_TAG, "[JPushNotiReceiver] 接收到推送下来的通知");
            int notiActionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            LogHelper.v(LOG_TAG, "[JPushNotiReceiver] 接收到推送下来的通知的Id:" + notiActionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogHelper.v(LOG_TAG, "[JPushNotiReceiver] 用户点击打开了通知");

            processNotification(context, bundle);
        }else if(JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())){
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            LogHelper.v(LOG_TAG, "[JPushNotiReceiver]" + intent.getAction() +" connected state change to "+connected);
        } else {
            LogHelper.v(LOG_TAG, "[JPushNotiReceiver] Unhandled intent - " + intent.getAction());
        }

    }

    private static String printBundle(Bundle bundle){
        StringBuilder sb = new StringBuilder();

        for(String key : bundle.keySet()){
            if(key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)){
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            }else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }

        return sb.toString();
    }

    /*
      处理自定义消息
     */
    private  void processCustomMessage(Context context, Bundle bundle){

    }

    private static final String PARAM_QUESTION_ID = "question_id";
    private static final String PARAM_ATRICLE_ID = "article_id";

    private static final String PARAM_ANSWER_ID = "answer_id";
    private static final String PARAM_QUESTION = "question";
    private static final String PARM_USER_ID = "user_id";

    public static final String INTENT_FLAG_NOTIFICATION = "intent_flag_notification";
    public static final int INTENT_FLAG_NOTIFICATION_VALUE = 8000; //表示通知方式打开

    /*
      处理通知消息
     */
    private void processNotification(Context context, Bundle bundle){

        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        try {
            Gson gson = new Gson();
            NotificationMsg notificationMsg = gson.fromJson(extras, NotificationMsg.class);

            if (notificationMsg.type == NotificationType.TYPE_INVITE_QUESTION
                    || notificationMsg.type == NotificationType.TYPE_COMMENT_AT_ME
                    || notificationMsg.type == NotificationType.TYPE_QUESTION_COMMENT
                    || notificationMsg.type == NotificationType.TYPE_MOD_QUESTION) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.putExtra(PARAM_QUESTION_ID, notificationMsg.id);
                intent.putExtra(INTENT_FLAG_NOTIFICATION, INTENT_FLAG_NOTIFICATION_VALUE);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);

            } else if (notificationMsg.type == NotificationType.TYPE_NEW_ANSWER
                    || notificationMsg.type == NotificationType.TYPE_ANSWER_AGREE
                    || notificationMsg.type == NotificationType.TYPE_ANSWER_THANK
                    || notificationMsg.type == NotificationType.TYPE_ANSWER_COMMENT_AT_ME
                    || notificationMsg.type == NotificationType.TYPE_ANSWER_AT_ME
                    || notificationMsg.type == NotificationType.TYPE_ANSWER_COMMENT) {
                Intent intent = new Intent();
                intent.putExtra(PARAM_ANSWER_ID, notificationMsg.id);
                intent.putExtra(PARAM_QUESTION, "Answer");
                intent.putExtra(INTENT_FLAG_NOTIFICATION, INTENT_FLAG_NOTIFICATION_VALUE);
                if (!WenJinApp.isAppLunched()) {
                    NotificationBuffer.setsIntent(intent);
                    NotificationBuffer.setObjClass(AnswerDetailActivity.class);
                    Intent intentMain = new Intent(context, WelcomeActivity.class);
                    intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intentMain);

                } else {
                    intent.setClass(context, AnswerDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
                Log.v("answer ", " activity started!  ");

            }else if(notificationMsg.type == NotificationType.TYPE_PEOPLE_FOCUS){

                Intent intent = new Intent();
                intent.putExtra(PARM_USER_ID, notificationMsg.id);
                intent.putExtra(PARAM_QUESTION, "Answer");
                intent.putExtra(INTENT_FLAG_NOTIFICATION, INTENT_FLAG_NOTIFICATION_VALUE);

                if (!WenJinApp.isAppLunched()) {
                    NotificationBuffer.setsIntent(intent);
                    NotificationBuffer.setObjClass(ProfileActivity.class);
                    Intent intentMain = new Intent(context, WelcomeActivity.class);
                    intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intentMain);
                } else {
                    intent.setClass(context, ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }else if(notificationMsg.type == NotificationType.TYPE_ARTICLE_NEW_COMMENT
                    ||notificationMsg.type == NotificationType.TYPE_ARTICLE_COMMENT_AT_ME){
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra(PARAM_ATRICLE_ID, notificationMsg.id);
                intent.putExtra(INTENT_FLAG_NOTIFICATION, INTENT_FLAG_NOTIFICATION_VALUE);
                if (!WenJinApp.isAppLunched()) {
                    NotificationBuffer.setsIntent(intent);
                    NotificationBuffer.setObjClass(ArticleActivity.class);
                    Intent intentMain = new Intent(context, WelcomeActivity.class);
                    intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intentMain);

                } else {
                    intent.setClass(context, ArticleActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            if(!WenJinApp.isAppLunched()){
                Intent intentMain = new Intent(context, WelcomeActivity.class);
                intentMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intentMain);
            }
        }

    }

    /*
      判断app是否在进程之中
     */
    private static boolean isAppInProcess(Context context){

        Boolean retBool = false;

        String packName = "com.twt.service.wenjin";

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appLists = activityManager.getRunningAppProcesses();

        for(ActivityManager.RunningAppProcessInfo running:appLists){
            Log.v("running process name:",running.processName);
            if(running.processName.equals(packName) ){
                retBool =  true;
                break;
            }
        }
        return retBool;
    }
}
