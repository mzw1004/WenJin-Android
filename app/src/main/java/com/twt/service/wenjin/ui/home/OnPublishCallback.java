package com.twt.service.wenjin.ui.home;

/**
 * Created by M on 2015/4/1.
 */
public interface OnPublishCallback {

    void publishSuccess(int questionId);

    void publishFailure(String errorMsg);

}
