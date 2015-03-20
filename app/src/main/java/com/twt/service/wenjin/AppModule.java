package com.twt.service.wenjin;

import dagger.Module;

/**
 * Created by M on 2015/3/19.
 */
@Module(
        injects = {
                WenJinApp.class
        }
)
public class AppModule {

    private WenJinApp app;

    public AppModule(WenJinApp app) {
        this.app = app;
    }

//    @Provides public Application provideApplication() {
//        return app;
//    }
}
