package com.twt.service.wenjin.ui.article;

import com.twt.service.wenjin.api.ApiClient;
import com.twt.service.wenjin.bean.Article;
import com.twt.service.wenjin.interactor.ArticleInteractor;

/**
 * Created by RexSun on 15/7/16.
 */
public class ArticlePresenterImpl implements ArticlePresenter, OnGetArticleCallback {

    private static final String LOG_TAG = ArticlePresenterImpl.class.getSimpleName();

    private ArticleView articleView;
    private ArticleInteractor articleInteractor;

    private boolean isAgree;
    private int agreeCount;
    private boolean isDisagree;

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
        isAgree = !isAgree;
        if (value == 1) {
            if (isAgree) {
                agreeCount++;
            } else {
                agreeCount--;
            }
            articleView.setAgree(isAgree, agreeCount);
        } else if (value == -1) {
            articleView.setDisagree(isDisagree);
        }

        ApiClient.voteArticle(articleId, value);


    }

    @Override
    public void onSuccess(Article article) {
        if (article.article_info.vote_value == 1) {
            isAgree = true;
        } else {
            isAgree = false;
        }
        if (article.article_info.vote_value == -1) {
            isDisagree = true;
        } else {
            isDisagree = false;
        }
        agreeCount = article.article_info.votes;
        articleView.bindArticleData(article);

    }

    @Override
    public void onFailure(String errorMsg) {
        articleView.toastMessage(errorMsg);

    }
}
