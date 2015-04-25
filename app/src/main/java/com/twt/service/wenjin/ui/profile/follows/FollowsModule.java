package com.twt.service.wenjin.ui.profile.follows;

import com.twt.service.wenjin.AppModule;
import com.twt.service.wenjin.interactor.FollowsInteracotrImpl;
import com.twt.service.wenjin.interactor.FollowsInteractor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2015/4/25.
 */
@Module(
        injects = FollowsActivity.class,
        addsTo = AppModule.class
)
public class FollowsModule {
    private FollowsView _followView;

    public FollowsModule(FollowsView followsView){
        _followView = followsView;
    }

    @Provides @Singleton public FollowsView provideFollowView(){return _followView;}

    @Provides @Singleton public FollowsPresenter provideFollowsPresenter(
            FollowsView followsView,
            FollowsInteractor followsInteractor){
        return new FollowsPresenterImpl(followsView, followsInteractor);
    }
}
