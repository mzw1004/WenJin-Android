package com.twt.service.wenjin.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.support.NetworkHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.main.MainActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        ApiClient.userLogout();
        mBtLogin.setOnClickListener(this);
    }

    @Override
    protected List<Object> getModlues() {
        return Arrays.<Object>asList(new LoginModule(this));
    }

    @Override
    public void onClick(View v) {
        mLoginPresenter.validateLogin(mEtUsername.getText().toString(), mEtPassword.getText().toString());
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
        inputMethodManager.hideSoftInputFromWindow(mEtPassword.getWindowToken(),0);
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
}
