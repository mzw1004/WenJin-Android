package com.twt.service.wenjin.interactor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/19.
 */
@Module(
        complete = false,
        library = true
)
public class InteractorsModule {

    @Provides @Singleton
    public LoginInteractor provideLoginInteractor() {
        return new LoginInteractorImpl();
    }

    @Provides @Singleton
    public HomeInteractor provideHomeInteractor() {
        return new HomeInteractorImpl();
    }

    @Provides @Singleton
    public ExploreInteractor provideExploreInteractor() {
        return new ExploreInteractorImpl();
    }

    @Provides @Singleton
    public QuestionInteractor provideQuestionInteractor() {
        return new QuestionInteractorImpl();
    }

    @Provides @Singleton
    public AnswerDetailInteractor provideAnswerDetailInteractor() {
        return new AnswerDetailInteractorImpl();
    }

    @Provides @Singleton
    public PublishInteractor providePublishInteractor() {
        return new PublishInteractorImpl();
    }

    @Provides @Singleton
    public AnswerInteractor provideAnswerInteractor() {
        return new AnswerInteractorImpl();
    }

    @Provides @Singleton
    public ProfileInteractor provideProfileInteractor() {
        return new ProfileInteractorImpl();
    }

    @Provides @Singleton
    public CommentInteractor provideCommentInteractor() {
        return new CommentInteractorImpl();
    }

    @Provides @Singleton
    public TopicListInteractor provideTopicListInteractor() {
        return new TopicListInteractorImpl();
    }

    @Provides @Singleton
    public TopicDetailInteractor provideTopicDetailInteractor() {
        return new TopicDetailInteractorImpl();
    }

    @Provides @Singleton ProfileAskanswerInteractor provideProfileAskanswerInteractor(){
        return new ProfileAskanswerInteractorImpl();
    }
}
