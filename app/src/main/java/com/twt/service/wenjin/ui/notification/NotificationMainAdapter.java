package com.twt.service.wenjin.ui.notification;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.notification.readlist.NotificationFragment;

/**
 * Created by Green on 15-6-27.
 */
public class NotificationMainAdapter extends FragmentPagerAdapter {

    private final String[] NOTIFICATION_TAB_TITLES = ResourceHelper.getStringArrays(R.array.notification_tab_titles);

    public NotificationMainAdapter(FragmentManager fm){super(fm);}

    @Override
    public CharSequence getPageTitle(int position) {
        return NOTIFICATION_TAB_TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment =  NotificationFragment.getInstance(position); //unread
                break;
            case 1:
                fragment =  NotificationFragment.getInstance(position);  //read
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NOTIFICATION_TAB_TITLES.length;
    }
}
