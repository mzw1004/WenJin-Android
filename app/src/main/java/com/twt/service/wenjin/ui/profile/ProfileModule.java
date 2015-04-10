package com.twt.service.wenjin.ui.profile;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.ProfileInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/4/5.
 */
@Module(
        injects = ProfileActivity.class,
        addsTo = AppModule.class
)
public class ProfileModule {

    private ProfileView mProfileView;

    public ProfileModule(ProfileView profileView) {
        this.mProfileView = profileView;
    }

    @Provides
    public ProfileView provideProfileView() {
        return mProfileView;
    }

    @Provides @Singleton
    public ProfilePresenter provideProfilePresenter(ProfileView view, ProfileInteractor interactor) {
        return new ProfilePresenterImpl(view, interactor);
    }
}
