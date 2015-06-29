package com.twt.service.wenjin.ui.notification;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.notification.readlist.NotificationFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Green on 15-6-27.
 */
public class NotificationMainAdapter extends FragmentPagerAdapter {

    private final static String LOG_TAG = NotificationMainAdapter.class.getSimpleName();
    private final String[] NOTIFICATION_TAB_TITLES = ResourceHelper.getStringArrays(R.array.notification_tab_titles);

    private HashMap<String,Fragment> mData;

    public NotificationMainAdapter(FragmentManager fm){
        super(fm);
        mData = new HashMap<>();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return NOTIFICATION_TAB_TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                if(mData.get(NOTIFICATION_TAB_TITLES[position]) == null) {
                    LogHelper.v(LOG_TAG,"new NotificationFragment[0]");
                    fragment = NotificationFragment.getInstance(position); //unread
                    mData.put(NOTIFICATION_TAB_TITLES[position], fragment);
                }else {
                    LogHelper.v(LOG_TAG,"exist NotificationFragment[0]");
                    fragment = mData.get(NOTIFICATION_TAB_TITLES[position]);
                }
                break;
            case 1:
                if(mData.get(NOTIFICATION_TAB_TITLES[position]) == null) {
                    LogHelper.v(LOG_TAG,"new NotificationFragment[1]");
                    fragment = NotificationFragment.getInstance(position); //unread
                    mData.put(NOTIFICATION_TAB_TITLES[position], fragment);
                }else {
                    LogHelper.v(LOG_TAG,"exist NotificationFragment[1]");
                    fragment = mData.get(NOTIFICATION_TAB_TITLES[position]);
                }
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return NOTIFICATION_TAB_TITLES.length;
    }
}
