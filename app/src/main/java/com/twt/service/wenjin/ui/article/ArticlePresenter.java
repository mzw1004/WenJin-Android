package com.twt.service.wenjin.ui.article;

/**
 * Created by RexSun on 15/7/16.
 */
public interface ArticlePresenter {
    void loadArticle(int articleId);

    void actionDownVote(int articleId, int value);
    void actionVote(int articleId, int value);
}
