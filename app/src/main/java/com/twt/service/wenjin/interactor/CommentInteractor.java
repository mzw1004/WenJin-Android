package com.twt.service.wenjin.interactor;

import android.view.View;

import com.twt.service.wenjin.ui.answer.comment.OnGetCommentCallback;
import com.twt.service.wenjin.ui.answer.comment.OnPublishCommentCallback;

/**
 * Created by M on 2015/4/6.
 */
public interface CommentInteractor {

    void loadComments(int answerId, OnGetCommentCallback callback);

    void publishComment(int answerId, String content,View view, OnPublishCommentCallback callback);

}
