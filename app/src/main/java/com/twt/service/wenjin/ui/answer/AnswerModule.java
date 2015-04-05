package com.twt.service.wenjin.ui.answer;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.AnswerInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/4/5.
 */
@Module(
        injects = AnswerActivity.class,
        addsTo = AppModule.class
)
public class AnswerModule {

    private AnswerView answerView;

    public AnswerModule(AnswerView answerView) {
        this.answerView = answerView;
    }

    @Provides
    public AnswerView provideAnswerView() {
        return answerView;
    }

    @Provides
    public AnswerPresenter providePresenter(AnswerView view, AnswerInteractor interactor) {
        return new AnswerPresenterImpl(view, interactor);
    }
}
