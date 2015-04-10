package com.twt.service.wenjin.ui.publish;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.PublishInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/4/5.
 */
@Module(
        injects = PublishActivity.class,
        addsTo = AppModule.class
)
public class PublishModule {

    private PublishView mView;

    public PublishModule(PublishView view) {
        this.mView = view;
    }

    @Provides @Singleton
    public PublishView providePublishView() {
        return mView;
    }

    @Provides @Singleton
    public PublishPresenter providePublishPresenter(PublishView view, PublishInteractor interactor) {
        return new PublishPresenterImpl(view, interactor);
    }

}
