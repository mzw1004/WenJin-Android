package com.twt.service.wenjin.ui.article.comment;

import com.twt.service.wenjin.bean.ArticleComment;

/**
 * Created by RexSun on 15/7/17.
 */
public interface OnGetCommentCallback {
    void onGetArticleCommentSuccess(ArticleComment[] comments);

    void onGetArticleCommentFailure(String errorMsg);
}
