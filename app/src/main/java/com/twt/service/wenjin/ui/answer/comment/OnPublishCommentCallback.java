package com.twt.service.wenjin.ui.answer.comment;

/**
 * Created by M on 2015/4/6.
 */
public interface OnPublishCommentCallback {

    void onPublishSuccess();

    void onPublishFailure(String errorMsg);

}
