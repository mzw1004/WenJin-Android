package com.twt.service.wenjin.ui.article.comment;

/**
 * Created by RexSun on 15/7/17.
 */
public interface OnPublishCommentCallback {
    void onPublishSuccess();

    void onPublishFailure(String errorMsg);
}
