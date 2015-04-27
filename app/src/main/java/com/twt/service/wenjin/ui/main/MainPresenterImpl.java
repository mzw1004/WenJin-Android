package com.twt.service.wenjin.ui.main;

/**
 * Created by M on 2015/3/20.
 */
public class MainPresenterImpl implements MainPresenter {

    private MainView mMainView;

    public MainPresenterImpl(MainView MainView) {
        this.mMainView = MainView;
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
            mMainView.replaceFragment(position);
            mMainView.setMainTitle(position);
    }
}
