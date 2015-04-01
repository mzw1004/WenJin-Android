package com.twt.service.wenjin.ui.home;

import android.view.View;

/**
 * Created by M on 2015/3/22.
 */
public interface HomePresenter {

    void refreshHomeItems();

    void loadMoreHomeItems();

    void publishQuestion(String title, String content, String attachKey, String topics);

    void onItemClicked(View v, int position);

}
