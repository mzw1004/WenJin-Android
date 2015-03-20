package com.twt.service.wenjin.ui.drawer;

import android.util.Log;

/**
 * Created by M on 2015/3/20.
 */
public class DrawerPresenterImpl implements DrawerPresenter {

    private static final String LOG_TAG = DrawerPresenterImpl.class.getSimpleName();

    DrawerView mDrawerView;

    public DrawerPresenterImpl(DrawerView drawerView) {
        this.mDrawerView = drawerView;
    }

    @Override
    public void select(int position) {
        Log.v(LOG_TAG, "clicked " + position);
    }
}
