package com.twt.service.wenjin.ui.drawer;

import com.twt.service.wenjin.support.LogUtil;

/**
 * Created by M on 2015/3/20.
 */
public class DrawerPresenterImpl implements DrawerPresenter {

    private static final String LOG_TAG = DrawerPresenterImpl.class.getSimpleName();

    DrawerView mDrawerView;

    private int mCurrentPosition = 0;

    public DrawerPresenterImpl(DrawerView drawerView) {
        this.mDrawerView = drawerView;
    }

    @Override
    public void selectItem(int position) {
        LogUtil.v(LOG_TAG, "clicked position: " + position);
        mCurrentPosition = position;
        mDrawerView.closeDrawer();
        mDrawerView.setSelectedItemColor(position);
        mDrawerView.sendDrawerItemClickedEvent(position);
    }
}
