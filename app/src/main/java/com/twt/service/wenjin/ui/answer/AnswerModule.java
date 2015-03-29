package com.twt.service.wenjin.ui.answer;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.AnswerInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/29.
 */
@Module(
        injects = AnswerActivity.class,
        addsTo = AppModule.class,
        library = true
)
public class AnswerModule {

    private AnswerView mAnswerView;

    public AnswerModule(AnswerView answerView) {
        this.mAnswerView = answerView;
    }

    @Provides @Singleton
    public AnswerView provideAnswerView() {
        return mAnswerView;
    }

    @Provides @Singleton
    public AnswerPresenter provideAnswerPresenter(AnswerView answerView, AnswerInteractor answerInteractor) {
        return new AnswerPresenterImpl(answerView, answerInteractor);
    }
}
