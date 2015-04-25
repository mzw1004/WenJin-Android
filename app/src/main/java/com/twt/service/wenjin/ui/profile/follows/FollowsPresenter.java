package com.twt.service.wenjin.ui.profile.follows;

import android.view.View;

/**
 * Created by Administrator on 2015/4/25.
 */
public interface FollowsPresenter {

    void onItemClicked(View v,int position);

    void loadMoreItems(String type,int uid);
}
