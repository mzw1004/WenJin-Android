package com.twt.service.wenjin.ui.article.comment;

import android.view.View;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.ArticleComment;
import com.twt.service.wenjin.interactor.ArticleCommentInteractor;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.answer.comment.CommetPresenterImpl;

/**
 * Created by RexSun on 15/7/17.
 */
public class CommentPresenterImpl implements CommentPresenter, OnPublishCommentCallback, OnGetCommentCallback {

    private static final String LOG_TAG = CommetPresenterImpl.class.getSimpleName();
    private CommentView commentView;
    private ArticleCommentInteractor interactor;
    private int articleId;

    public CommentPresenterImpl(CommentView commentView, ArticleCommentInteractor interactor) {
        this.commentView = commentView;
        this.interactor = interactor;
    }

    @Override
    public void onGetArticleCommentSuccess(ArticleComment[] comments) {
        commentView.hideProgressBar();
        commentView.bindComment(comments);

    }

    @Override
    public void onGetArticleCommentFailure(String errorMsg) {
        commentView.toastMessage(errorMsg);
    }

    @Override
    public void onPublishSuccess(View view) {
        this.loadComment(articleId);
        commentView.clearTextContent();
        commentView.toastMessage(ResourceHelper.getString(R.string.comment_success));
        view.setClickable(true);

    }

    @Override
    public void onPublishFailure(String errorMsg, View view) {

        commentView.toastMessage(errorMsg);
        view.setClickable(true);
    }

    @Override
    public void loadComment(int articleId) {
        commentView.showProgressBar();
        interactor.loadComment(articleId, this);
    }

    @Override
    public void publishComment(int articleId, String message, View view) {
        this.articleId = articleId;
        interactor.publishComment(articleId, message,view, this);
    }

    @Override
    public void onItemClicked(View view, int position) {
        commentView.addAtUser(position);

    }
}
