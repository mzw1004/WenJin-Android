package com.twt.service.wenjin.ui.article;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Article;
import com.twt.service.wenjin.interactor.ArticleInteractor;
import com.twt.service.wenjin.support.ResourceHelper;

/**
 * Created by RexSun on 15/7/16.
 */
public class ArticlePresenterImpl implements ArticlePresenter, OnGetArticleCallback {

    private static final String LOG_TAG = ArticlePresenterImpl.class.getSimpleName();

    private ArticleView articleView;
    private ArticleInteractor articleInteractor;
    private static final int VOTE_STATE_UPVOTE = 1;
    private static final int VOTE_STATE_DOWNVOTE = -1;
    private static final int VOTE_STATE_NONE = 0;
    private int agreeCount;

    private int voteState;

    public ArticlePresenterImpl(ArticleView articleView, ArticleInteractor articleInteractor) {
        this.articleView = articleView;
        this.articleInteractor = articleInteractor;
    }

    @Override
    public void loadArticle(int articleId) {

        articleView.showProgressBar();
        articleInteractor.getArticle(articleId, this);
        articleView.hideProgressBar();

    }

    @Override
    public void actionVote(int articleId, int value) {
        if (voteState == VOTE_STATE_NONE || voteState == VOTE_STATE_DOWNVOTE){
            voteState = VOTE_STATE_UPVOTE;
            agreeCount++;
            articleView.toastMessage(ResourceHelper.getString(R.string.action_upvote_msg));
        }else {
            voteState = VOTE_STATE_NONE;
            agreeCount--;
            articleView.toastMessage(ResourceHelper.getString(R.string.action_cancel_upvote_msg));
        }
        ApiClient.voteArticle(articleId, value);
        articleView.setAgree(voteState, agreeCount);

    }

    @Override
    public void actionDownVote(int articleID, int value) {
        if(voteState == VOTE_STATE_UPVOTE){
            agreeCount--;
            articleView.setAgreeCount(agreeCount);
            voteState = VOTE_STATE_DOWNVOTE;
            articleView.toastMessage(ResourceHelper.getString(R.string.action_downvote_msg));
        }else if(voteState == VOTE_STATE_NONE){
            voteState = VOTE_STATE_DOWNVOTE;
            articleView.toastMessage(ResourceHelper.getString(R.string.action_downvote_msg));
        }else {
            voteState = VOTE_STATE_NONE;
            articleView.toastMessage(ResourceHelper.getString(R.string.action_cancel_downvote_msg));
        }
        ApiClient.voteArticle(articleID, value);
        articleView.setDisagree(voteState);
    }

    @Override
    public void onSuccess(Article article) {
        voteState = article.article_info.vote_value;
        agreeCount = article.article_info.votes;
        articleView.bindArticleData(article);

    }

    @Override
    public void onFailure(String errorMsg) {
        articleView.toastMessage(errorMsg);

    }
}
