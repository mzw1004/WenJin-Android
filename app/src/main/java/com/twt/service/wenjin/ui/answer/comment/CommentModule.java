package com.twt.service.wenjin.ui.answer.comment;

import com.twt.service.wenjin.interactor.CommentInteractor;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/4/6.
 */
@Module(
        injects = CommentActivity.class,
        addsTo = AnswerDetailModule.class
)
public class CommentModule {

    private CommentView mCommentView;

    public CommentModule(CommentView commentView) {
        this.mCommentView = commentView;
    }

    @Provides @Singleton
    public CommentView provideCommentView() {
        return mCommentView;
    }

    @Provides @Singleton
    public CommentPresenter provideCommentPresenter(CommentView view, CommentInteractor interactor) {
        return new CommetPresenterImpl(view, interactor);
    }
}
