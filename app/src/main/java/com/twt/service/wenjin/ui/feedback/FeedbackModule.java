package com.twt.service.wenjin.ui.feedback;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.FeedbackInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/4/18.
 */
@Module(
        injects = FeedbackActivity.class,
        addsTo = AppModule.class
)
public class FeedbackModule {

    private FeedbackView mFeedbackView;

    public FeedbackModule(FeedbackView feedbackView) {
        this.mFeedbackView = feedbackView;
    }

    @Provides @Singleton
    public FeedbackView provideFeedbackView() {
        return mFeedbackView;
    }

    @Provides @Singleton
    public FeedbackPresenter provideFeedbackPresenter(FeedbackView view, FeedbackInteractor interactor) {
        return new FeedbackPresenterImpl(view, interactor);
    }
}
