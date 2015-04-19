package com.twt.service.wenjin.ui.profile.askanswer;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.ProfileAskanswerInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2015/4/17.
 */
@Module(
        injects = ProfileAskanswerActivity.class,
        addsTo = AppModule.class
)

public class ProfileAskanswerModule {

    private ProfileAskanswerView _profileAskanswerView;

    public ProfileAskanswerModule(ProfileAskanswerView profileAskanswerView){
        _profileAskanswerView = profileAskanswerView;
    }

    @Provides @Singleton public ProfileAskanswerView provideProfileAskanswerView(){return _profileAskanswerView;}

    @Provides @Singleton public ProfileAskanswerPresenter provideProfileAskanswerPresenter(
            ProfileAskanswerView view,
            ProfileAskanswerInteractor interactor){
        return new ProfileAskanswerPresenterImpl(view,interactor);
    }

}
