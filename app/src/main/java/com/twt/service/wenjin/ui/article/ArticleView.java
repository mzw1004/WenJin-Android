package com.twt.service.wenjin.ui.article;

import com.twt.service.wenjin.bean.Article;

/**
 * Created by RexSun on 15/7/16.
 */
public interface ArticleView {

    void showProgressBar();

    void hideProgressBar();

    void bindArticleData(Article article);

    void toastMessage(String msg);

    void setAgree(boolean isAgree, int agreeCount);

    void setDisagree(boolean isDisagree);

    void startProfileActivity();

    void startCommentActivity();

}
