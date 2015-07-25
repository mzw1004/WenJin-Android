package com.twt.service.wenjin.ui.article.comment;

import android.view.View;

/**
 * Created by RexSun on 15/7/17.
 */
public interface CommentPresenter {

    void loadComment(int articleId);

    void publishComment(int articleId, String message);

    void onItemClicked(View view, int position);
}
