package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.profile.follows.OnGetFollowCallback;

/**
 * Created by Administrator on 2015/4/25.
 */
public interface FollowsInteractor {

    void getFollowersItems(int uid,int page,int perPage,OnGetFollowCallback onGetFollowCallback);

    void getFollowingItems(int uid,int page,int perPage,OnGetFollowCallback onGetFollowCallback);
}
