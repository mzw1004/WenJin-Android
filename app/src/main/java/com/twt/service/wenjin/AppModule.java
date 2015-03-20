package com.twt.service.wenjin;

import android.app.Application;
import android.content.Context;

import com.twt.service.wenjin.interactor.InteractorsModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/19.
 */
@Module(
        injects = {
                WenJinApp.class
        },
        includes = {
                InteractorsModule.class
        }
)
public class AppModule {

    private WenJinApp app;

    public AppModule(WenJinApp app) {
        this.app = app;
    }

    @Provides @Singleton public Context provideApplicationContext() {
        return app;
    }
}
