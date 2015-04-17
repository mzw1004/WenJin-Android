package com.twt.service.wenjin.ui.topic.detail;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.TopicDetailInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/4/11.
 */
@Module(
        injects = TopicDetailActivity.class,
        addsTo = AppModule.class
)
public class TopicDetailModule {

    private TopicDetailView mTopicDetailView;

    public TopicDetailModule(TopicDetailView topicDetailView) {
        this.mTopicDetailView = topicDetailView;
    }

    @Provides @Singleton
    public TopicDetailView provideTopicDetailView() {
        return mTopicDetailView;
    }

    @Provides @Singleton
    public TopicDetailPresenter provideTopicDetailPresenter(TopicDetailView view, TopicDetailInteractor interactor) {
        return new TopicDetailPresenterImpl(view, interactor);
    }
}
