package com.twt.service.wenjin.ui.profile.askanswer;

import android.view.View;

/**
 * Created by Administrator on 2015/4/17.
 */
public interface ProfileAskanswerPresenter {

    void loadMoreItems(String type);

    void refreshItems(String type);

    void onItemClicked(View v, int position);

}
