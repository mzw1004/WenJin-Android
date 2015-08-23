package com.twt.service.wenjin.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.support.JPushHelper;
import com.twt.service.wenjin.support.StatusBarHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.login.greenchannel.GreenChannelActivity;
import com.twt.service.wenjin.ui.main.MainActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

    private static final String LOG_TAG = LoginActivity.class.getSimpleName();

    @Inject
    LoginPresenter mLoginPresenter;
    @InjectView(R.id.tv_login_username)
    EditText mEtUsername;
    @InjectView(R.id.tv_login_password)
    EditText mEtPassword;
    @InjectView(R.id.bt_login)
    Button mBtLogin;
    @InjectView(R.id.pb_login)
    ProgressBar mPbLogin;
    @InjectView(R.id.iv_login_logo)
    ImageView ivLoginLogo;
    @InjectView(R.id.bt_green_channel)
    TextView btGreenChannel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);


        JPushInterface.clearAllNotifications(getApplicationContext());
        JPushHelper jPushHelper = new JPushHelper(null,null);
        jPushHelper.cancelAlias();

        ApiClient.userLogout();


        mBtLogin.setOnClickListener(this);
        btGreenChannel.setOnClickListener(this);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new LoginModule(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_login:
                mLoginPresenter.validateLogin(mEtUsername.getText().toString(), mEtPassword.getText().toString());
                break;
            case R.id.bt_green_channel:
                GreenChannelActivity.actionStart(this);
        }

    }

    @Override
    public void usernameError(String errorString) {
        mEtUsername.setError(errorString);
    }

    @Override
    public void passwordError(String errorString) {
        mEtPassword.setError(errorString);
    }

    @Override
    public void showProgressBar() {
        mPbLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mPbLogin.setVisibility(View.GONE);
    }

    @Override
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEtPassword.getWindowToken(), 0);
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        StatusBarHelper.setStatusBar(this);
    }
}
