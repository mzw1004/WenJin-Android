package com.twt.service.wenjin.ui.draft;

import com.twt.service.wenjin.ui.main.MainModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/5/17.
 */
@Module(
        injects = DraftFragment.class,
        addsTo = MainModule.class
)
public class DraftModule {

    private DraftView mView;

    public DraftModule(DraftView view) {
        this.mView = view;
    }

    @Provides
    @Singleton
    public DraftView provideDraftView() {
        return mView;
    }

    @Provides
    @Singleton
    public DraftPresenter provideDraftPresenter(DraftView view) {
        return new DraftPresenterImpl(view);
    }
}
