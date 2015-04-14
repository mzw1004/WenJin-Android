package com.twt.service.wenjin.ui.topic.list;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.TopicListInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/4/8.
 */
@Module(
        injects = TopicListFragment.class,
        addsTo = AppModule.class
)
public class TopicListModule {

    private TopicListView mTopicListView;

    public TopicListModule(TopicListView view) {
        this.mTopicListView = view;
    }

    @Provides @Singleton
    public TopicListView provideTopicListView() {
        return mTopicListView;
    }

    @Provides @Singleton
    public TopicListPresenter provideTopicListPresenter(TopicListView view, TopicListInteractor interactor) {
        return new TopicListPresenterImpl(view, interactor);
    }
}
