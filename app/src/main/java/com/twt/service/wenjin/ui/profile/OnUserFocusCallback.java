package com.twt.service.wenjin.ui.profile;

/**
 * Created by M on 2015/4/17.
 */
public interface OnUserFocusCallback {

    void onFocusSuccess(boolean isfocused);

    void onFocusFailure(String errorMsg);

}
