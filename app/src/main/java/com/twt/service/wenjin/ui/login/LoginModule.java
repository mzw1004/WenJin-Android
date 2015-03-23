package com.twt.service.wenjin.ui.login;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.LoginInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by M on 2015/3/23.
 */
@Module(
        injects = LoginActivity.class,
        addsTo = AppModule.class
)
public class LoginModule {

    private LoginView mLoginView;

    public LoginModule(LoginView loginView) {
        this.mLoginView = loginView;
    }

    @Provides
    @Singleton
    public LoginView provideLoginView() {
        return mLoginView;
    }

    @Provides
    @Singleton
    public LoginPresenter provideLoginPresenter(LoginView loginView, LoginInteractor loginInteractor) {
        return new LoginPresenterImpl(loginView, loginInteractor);
    }
}
