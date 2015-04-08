package com.twt.service.wenjin.ui.topic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.topic.list.TopicListFragment;

/**
 * Created by M on 2015/4/8.
 */
public class TopicAdapter extends FragmentPagerAdapter {

    private final String[] TAB_TITLES = ResourceHelper.getStringArrays(R.array.topic_tab_titles);

    public TopicAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        return TopicListFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
