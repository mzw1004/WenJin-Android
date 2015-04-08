package com.twt.service.wenjin.ui.topic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.Bundler;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.ui.topic.list.TopicListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TopicFragment extends Fragment {

    @InjectView(R.id.viewpager_tab_topic)
    SmartTabLayout mTabLayout;
    @InjectView(R.id.viewpager_topic)
    ViewPager mViewPager;

    public TopicFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.inject(this, rootView);

        FragmentPagerAdapter adapter = new TopicAdapter(getActivity().getSupportFragmentManager());

        mViewPager.setAdapter(adapter);
        mTabLayout.setViewPager(mViewPager);

        return rootView;
    }

}
