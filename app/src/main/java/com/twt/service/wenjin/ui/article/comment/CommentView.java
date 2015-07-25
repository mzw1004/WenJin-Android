package com.twt.service.wenjin.ui.article.comment;

import com.twt.service.wenjin.bean.ArticleComment;

/**
 * Created by RexSun on 15/7/17.
 */
public interface CommentView {
    void bindComment(ArticleComment[] comments);

    void addAtUser(int position);

    void clearTextContent();

    void showProgressBar();

    void hideProgressBar();

    void toastMessage(String msg);
}
