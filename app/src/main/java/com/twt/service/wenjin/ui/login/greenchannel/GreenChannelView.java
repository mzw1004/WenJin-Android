package com.twt.service.wenjin.ui.login.greenchannel;

/**
 * Created by RexSun on 15/7/18.
 */
public interface GreenChannelView {
    void showProgressBar();

    void hideProgressBar();

    void toastMessage(String msg);

    void showWebView(String greenChannelUrl);

}
