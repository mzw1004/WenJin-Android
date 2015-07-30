package com.twt.service.wenjin.ui.article.comment;

import android.view.View;

/**
 * Created by RexSun on 15/7/17.
 */
public interface OnPublishCommentCallback {
    void onPublishSuccess(View view);

    void onPublishFailure(String errorMsg, View view);
}
