package com.twt.service.wenjin.ui.notification;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.gson.Gson;
import com.twt.service.wenjin.bean.NotificationMsg;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailActivity;
import com.twt.service.wenjin.ui.main.MainActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;

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

    private static final String PARAM_ANSWER_ID = "answer_id";
    private static final String PARAM_QUESTION = "question";

    /*
      处理通知消息
     */
    private void processNotification(Context context, Bundle bundle){

        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);

        Gson gson = new Gson();
        NotificationMsg notificationMsg = gson.fromJson(extras, NotificationMsg.class);

        if(notificationMsg.type == NotificationType.TYPE_NEW_ANSWER
                || notificationMsg.type == NotificationType.TYPE_INVITE_QUESTION
                || notificationMsg.type == NotificationType.TYPE_QUESTION_COMMENT
                || notificationMsg.type == NotificationType.TYPE_MOD_QUESTION){
            Intent intent = new Intent(context, QuestionActivity.class);
            intent.putExtra(PARAM_QUESTION_ID, notificationMsg.id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);

        }else if(notificationMsg.type == NotificationType.TYPE_ANSWER_AGREE
                ||notificationMsg.type == NotificationType.TYPE_ANSWER_THANK
                ||notificationMsg.type == NotificationType.TYPE_ANSWER_COMMENT_AT_ME
                ||notificationMsg.type == NotificationType.TYPE_ANSWER_AT_ME
                ||notificationMsg.type == NotificationType.TYPE_ANSWER_COMMENT){
            Intent intent = new Intent(context, AnswerDetailActivity.class);
            intent.putExtra(PARAM_ANSWER_ID, notificationMsg.id);
            intent.putExtra(PARAM_QUESTION, "Answer");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        }



        //打开自定义的Activity
        //Intent intent = new Intent(context, QuestionActivity.class);
        //intent.putExtras(bundle);
        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
        //context.startActivity(intent);
    }

    /*
      判断app是否在进程之中
     */
    private static boolean isAppInProcess(Context context){
        String packName = "com.twt.service.wenjin";
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> procsInfo = activityManager.getRunningAppProcesses();

        if(procsInfo == null) return false;

        Iterator<ActivityManager.RunningAppProcessInfo> iter = procsInfo.iterator();
        while (iter.hasNext()){
            if(iter.next().processName.equals(packName)){
                return true;
            }

        }
        return  false;
    }
}
