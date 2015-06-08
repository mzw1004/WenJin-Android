package com.twt.service.wenjin.ui.welcome;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;

import com.activeandroid.query.Select;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.CrashInfo;
import com.twt.service.wenjin.support.JPushHelper;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.NetworkHelper;
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.ui.login.LoginActivity;
import com.twt.service.wenjin.ui.main.MainActivity;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

public class WelcomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        List<CrashInfo> crashInfos = new Select()
                .from(CrashInfo.class)
                .execute();
        LogHelper.d(LogHelper.makeLogTag(this.getClass()), "crash info number: " + crashInfos.size());
        if (crashInfos.size() > 0 && NetworkHelper.isOnline()) {
            for (int i = 0; i < crashInfos.size(); i++) {
                CrashInfo crashInfo = crashInfos.get(i);
                ApiClient.publishFeedback("Android Crash Info", crashInfo.detail, new JsonHttpResponseHandler());
                crashInfo.delete();
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (PrefUtils.isLogin()) {
                    intent = new Intent(WelcomeActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                }
                WelcomeActivity.this.startActivity(intent);
                WelcomeActivity.this.finish();
            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }
}
