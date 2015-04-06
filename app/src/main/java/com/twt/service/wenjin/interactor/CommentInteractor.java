package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.answer.comment.OnGetCommentCallback;

/**
 * Created by M on 2015/4/6.
 */
public interface CommentInteractor {

    void loadComments(int answerId, OnGetCommentCallback callback);

}
