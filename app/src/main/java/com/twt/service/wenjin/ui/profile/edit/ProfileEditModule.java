package com.twt.service.wenjin.ui.profile.edit;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.ProfileEditInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rex on 2015/7/25.
 */
@Module(
        injects = ProfileEditActivity.class,
        addsTo = AppModule.class
)
public class ProfileEditModule {
    private ProfileEditView view;

    public ProfileEditModule(ProfileEditView view) {
        this.view = view;
    }

    @Provides
    ProfileEditView provideProfileEditView() {
        return view;
    }

    @Provides
    @Singleton
    ProfileEditPresenter provideProfileEditPresenter(ProfileEditView view, ProfileEditInteractor interactor) {
        return new ProfileEditPresenterImpl(view, interactor);
    }
}
