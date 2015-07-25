package com.twt.service.wenjin.ui.article.comment;

import com.twt.service.wenjin.interactor.ArticleCommentInteractor;
import com.twt.service.wenjin.ui.article.ArticleModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by RexSun on 15/7/17.
 */
@Module(
        injects = CommentActivlty.class,
        addsTo = ArticleModule.class
)
public class CommentModule {
    private CommentView commentView;

    public CommentModule(CommentView commentView) {
        this.commentView = commentView;
    }

    @Provides
    @Singleton
    public CommentView provideCommentView() {
        return commentView;
    }

    @Provides @Singleton
    public CommentPresenter provideCommentPresenter(CommentView view, ArticleCommentInteractor interactor) {
        return new CommentPresenterImpl(view, interactor);
    }


}
