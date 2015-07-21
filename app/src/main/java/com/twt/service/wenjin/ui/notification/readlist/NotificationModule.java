package com.twt.service.wenjin.ui.notification.readlist;

import com.twt.service.wenjin.interactor.NotificationInteractor;
import com.twt.service.wenjin.ui.main.MainModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Green on 15-6-22.
 */
@Module(
        injects = {
            NotificationFragment.class
        },
        addsTo = MainModule.class,
        library = true
)
public class NotificationModule {
    private NotificationView mNotificationView;

    public NotificationModule(NotificationView argNotificationView){
        this.mNotificationView = argNotificationView;
    }


    @Provides
    @Singleton
    public NotificationView provideNotificationView(){return this.mNotificationView;}


    @Provides
    @Singleton
    public NotificationPresenter provideNotificationPresenter(NotificationView argNotificationView,
                                                              NotificationInteractor argNotificationInteractor){

        return new NotificationPresenterImpl(argNotificationView, argNotificationInteractor);

    }

}
