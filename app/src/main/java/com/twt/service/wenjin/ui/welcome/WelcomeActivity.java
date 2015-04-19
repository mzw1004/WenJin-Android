package com.twt.service.wenjin.ui.welcome;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.PrefUtils;
import com.twt.service.wenjin.ui.login.LoginActivity;
import com.twt.service.wenjin.ui.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class WelcomeActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.RGBA_8888);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

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
        }, 3000);
    }

}
