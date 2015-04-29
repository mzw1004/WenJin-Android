package com.twt.service.wenjin.ui.explore.list;

import android.view.View;

/**
 * Created by WGL on 2015/3/28.
 */
public interface ExploreListPresenter {

    void loadExploreItems(int type);

    void firstTimeLoadExploreItems(int type);

    void loadMoreExploreItems(int type);

    void onItemClicked(View v, int position);

}
