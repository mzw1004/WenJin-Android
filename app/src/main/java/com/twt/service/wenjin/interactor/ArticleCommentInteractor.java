package com.twt.service.wenjin.interactor;

import android.view.View;

import com.twt.service.wenjin.ui.article.comment.OnGetCommentCallback;
import com.twt.service.wenjin.ui.article.comment.OnPublishCommentCallback;

/**
 * Created by RexSun on 15/7/19.
 */
public interface ArticleCommentInteractor {

    void loadComment(int articleId, OnGetCommentCallback onGetCommentCallback);

    void publishComment(int articleId, String content,View view, OnPublishCommentCallback onPublishCommentCallback);
}
