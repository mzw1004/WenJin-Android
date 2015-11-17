package com.twt.service.wenjin.ui.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.explore.list.ExploreListFragment;

/**
 * Created by Green on 15/11/16.
 */
public class SearchFragmentAdapter extends FragmentPagerAdapter {

    private final String[] SEARCH_TAB_TITLES = ResourceHelper.getStringArrays(R.array.search_tab_titles);

    public SearchFragmentAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return SEARCH_TAB_TITLES[position];
    }

    @Override
    public Fragment getItem(int position) {
        return ExploreListFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return SEARCH_TAB_TITLES.length;
    }
}
