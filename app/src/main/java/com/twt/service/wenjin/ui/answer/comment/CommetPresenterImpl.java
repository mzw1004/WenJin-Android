package com.twt.service.wenjin.ui.answer.comment;

import android.view.View;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.Comment;
import com.twt.service.wenjin.interactor.CommentInteractor;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;

/**
 * Created by M on 2015/4/6.
 */
public class CommetPresenterImpl implements
        CommentPresenter, OnGetCommentCallback, OnPublishCommentCallback {

    private static final String LOG_TAG = CommetPresenterImpl.class.getSimpleName();

    private CommentView mView;
    private CommentInteractor mInteractor;
    private int answerId;

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
    public void publishComment(int answerId, String content, View view) {
        this.answerId = answerId;
        mInteractor.publishComment(answerId, content,view, this);
    }

    @Override
    public void onItemClicked(View view, int position) {
        mView.addAtUser(position);
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

    @Override
    public void onPublishSuccess(View view) {
        this.loadComments(answerId);
        mView.clearTextContent();
        mView.toastMessage(ResourceHelper.getString(R.string.comment_success));
        view.setClickable(true);
    }

    @Override
    public void onPublishFailure(String errorMsg, View view) {
        mView.toastMessage(errorMsg);
        view.setClickable(true);
    }
}
