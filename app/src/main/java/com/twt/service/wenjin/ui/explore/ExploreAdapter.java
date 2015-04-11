package com.twt.service.wenjin.ui.explore;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.explore.list.ExploreListFragment;

/**
 * Created by Administrator on 2015/4/11.
 */
public class ExploreAdapter extends FragmentPagerAdapter {

    private final String[] EXPLORE_TAB_TITLES = ResourceHelper.getStringArrays(R.array.explore_tab_titles);

    public ExploreAdapter(android.support.v4.app.FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return EXPLORE_TAB_TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        return ExploreListFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return EXPLORE_TAB_TITLES.length;
    }
}
