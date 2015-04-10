package com.twt.service.wenjin.ui.question;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.QuestionInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/27.
 */
@Module(
        injects = QuestionActivity.class,
        addsTo = AppModule.class,
        library = true
)
public class QuestionModule {

    private QuestionView mQuestionView;

    public QuestionModule(QuestionView questionView) {
        this.mQuestionView = questionView;
    }

    @Provides @Singleton
    public QuestionView provideQusetionView() {
        return mQuestionView;
    }

    @Provides @Singleton
    public QuestionPresenter provideQuestionPresenter(QuestionView questionView, QuestionInteractor questionInteractor) {
        return new QuestionPresenterImpl(questionView, questionInteractor);
    }
}
