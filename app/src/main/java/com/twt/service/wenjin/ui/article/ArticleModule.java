package com.twt.service.wenjin.ui.article;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.ArticleInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RexSun on 15/7/16.
 */

@Module(
        injects = ArticleActivity.class,
        addsTo = AppModule.class,
        library = true
)
public class ArticleModule {

    private ArticleView articleView;

    public ArticleModule(ArticleView articleView) {
        this.articleView = articleView;
    }

    @Provides @Singleton
    public ArticleView provideArticleView() {
        return articleView;
    }

    @Provides @Singleton
    public ArticlePresenter providesArticlePresenter(ArticleView view, ArticleInteractor interactor){
        return new ArticlePresenterImpl(view, interactor);
    }
}
