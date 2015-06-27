package com.twt.service.wenjin.ui.notification;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.twt.service.wenjin.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Green on 15-6-27.
 */
public class NotificationMainFragment extends Fragment {

    @InjectView(R.id.tabs_notification_main)
    PagerSlidingTabStrip mPagerSlidingTabStrip;

    @InjectView(R.id.viewpager_notification_main)
    ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_main, container, false);
        ButterKnife.inject(this, rootView);

        FragmentPagerAdapter fragmentPagerAdapter = new NotificationMainAdapter(getChildFragmentManager());
        mViewPager.setAdapter(fragmentPagerAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
