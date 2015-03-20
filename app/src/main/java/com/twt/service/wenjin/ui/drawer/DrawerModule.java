package com.twt.service.wenjin.ui.drawer;

import com.twt.service.wenjin.ui.main.MainModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/20.
 */
@Module(
        injects = {
                DrawerFragment.class
        },
        addsTo = MainModule.class,
        library = true
)
public class DrawerModule {

    private DrawerView view;

    public DrawerModule(DrawerView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    DrawerView provideDrawerView() {
        return view;
    }

    @Provides
    @Singleton
    DrawerPresenter provideDrawerPresenter(DrawerView view) {
        return new DrawerPresenterImpl(view);
    }
}
