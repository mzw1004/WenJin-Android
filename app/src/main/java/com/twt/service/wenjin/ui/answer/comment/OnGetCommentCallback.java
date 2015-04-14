package com.twt.service.wenjin.ui.answer.comment;

import com.twt.service.wenjin.bean.Comment;

/**
 * Created by M on 2015/4/6.
 */
public interface OnGetCommentCallback {

    void onGetCommentSuccess(Comment[] comments);

    void onGetCommentFailure(String errorMsg);

}
