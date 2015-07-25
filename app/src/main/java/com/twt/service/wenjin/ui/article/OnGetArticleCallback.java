package com.twt.service.wenjin.ui.article;

import com.twt.service.wenjin.bean.Article;

/**
 * Created by RexSun on 15/7/16.
 */
public interface OnGetArticleCallback {

    void onSuccess(Article article);
    void onFailure(String errorMsg);
}
