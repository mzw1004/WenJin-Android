package com.twt.service.wenjin.ui.answer.comment;

import com.twt.service.wenjin.bean.Comment;

/**
 * Created by M on 2015/4/6.
 */
public interface CommentView {

    void bindComment(Comment[] comments);

    void addAtUser(int position);

    void clearTextContent();

    void showProgressBar();

    void hideProgressBar();

    void toastMessage(String msg);
}
