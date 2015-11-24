package com.twt.service.wenjin.ui.explore;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twt.service.wenjin.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/4/11.
 */
public class ExploreFragment extends Fragment {

    @Bind(R.id.tabs_explore)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager_explore)
    ViewPager _viewPager;

    public ExploreFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_explore,container,false);
        ButterKnife.bind(this, rootView);

        FragmentPagerAdapter fragmentPagerAdapter = new ExploreAdapter(getChildFragmentManager());
        _viewPager.setAdapter(fragmentPagerAdapter);
        mTabLayout.setupWithViewPager(_viewPager);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
