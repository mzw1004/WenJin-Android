package com.twt.service.wenjin.ui.answer.comment;

import android.view.View;

/**
 * Created by M on 2015/4/6.
 */
public interface CommentPresenter {

    void loadComments(int answerId);

    void publishComment(int answerId, String content, View view);

    void onItemClicked(View view, int position);

}
