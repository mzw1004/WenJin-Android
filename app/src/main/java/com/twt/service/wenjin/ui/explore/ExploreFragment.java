package com.twt.service.wenjin.ui.explore;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.twt.service.wenjin.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2015/4/11.
 */
public class ExploreFragment extends Fragment {

    @InjectView(R.id.tabs_explore)
    PagerSlidingTabStrip _pagerSlidingTabStrip;

    @InjectView(R.id.viewpager_explore)
    ViewPager _viewPager;

    public ExploreFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore,container,false);
        ButterKnife.inject(this, rootView);

        FragmentPagerAdapter fragmentPagerAdapter = new ExploreAdapter(getChildFragmentManager());
        _viewPager.setAdapter(fragmentPagerAdapter);
        _pagerSlidingTabStrip.setViewPager(_viewPager);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
