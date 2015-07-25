package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.article.OnGetArticleCallback;

/**
 * Created by RexSun on 15/7/16.
 */
public interface ArticleInteractor {

    public void getArticle(int articleId, OnGetArticleCallback onGetArticleCallback);
}
