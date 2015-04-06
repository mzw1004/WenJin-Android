package com.twt.service.wenjin.ui.answer.comment;

import com.twt.service.wenjin.bean.Comment;
import com.twt.service.wenjin.interactor.CommentInteractor;
import com.twt.service.wenjin.support.LogHelper;

/**
 * Created by M on 2015/4/6.
 */
public class CommetPresenterImpl implements CommentPresenter, OnGetCommentCallback {

    private static final String LOG_TAG = CommetPresenterImpl.class.getSimpleName();

    private CommentView mView;
    private CommentInteractor mInteractor;

    public CommetPresenterImpl(CommentView view, CommentInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void loadComments(int answerId) {
        mView.showProgressBar();
        mInteractor.loadComments(answerId, this);
    }

    @Override
    public void onGetCommentSuccess(Comment[] comments) {
        mView.hideProgressBar();
        mView.bindComment(comments);
    }

    @Override
    public void onGetCommentFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }
}
