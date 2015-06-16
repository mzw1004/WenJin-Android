package com.twt.service.wenjin.receiver;

import android.content.Intent;

/**
 * Created by Green on 15-6-16.
 */
public class NotificationBuffer {

    private static Intent sIntent = null;

    public static Intent getsIntent(){
        if(sIntent != null){
            return sIntent;
        }else {
            return null;
        }
    }

    public static void setsIntent(Intent argIntent){ sIntent = argIntent;}


}
