package com.twt.service.wenjin.ui.answer.comment;

import android.view.View;

/**
 * Created by M on 2015/4/6.
 */
public interface OnPublishCommentCallback {

    void onPublishSuccess(View view);

    void onPublishFailure(String errorMsg, View view);

}
