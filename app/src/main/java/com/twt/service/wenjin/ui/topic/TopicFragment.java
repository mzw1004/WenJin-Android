package com.twt.service.wenjin.ui.topic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.topic.list.TopicListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TopicFragment extends Fragment {

    private static final String LOG_TAG = TopicFragment.class.getSimpleName();

    @InjectView(R.id.tabs_topic)
    PagerSlidingTabStrip mTabs;
    @InjectView(R.id.viewpager_topic)
    ViewPager mViewPager;

    public TopicFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topic, container, false);
        ButterKnife.inject(this, rootView);

        LogHelper.d(LOG_TAG, "onCreateView");
        FragmentPagerAdapter adapter = new TopicAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        mTabs.setViewPager(mViewPager);

        return rootView;
    }

}
