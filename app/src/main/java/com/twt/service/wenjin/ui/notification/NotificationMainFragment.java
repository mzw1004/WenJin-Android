package com.twt.service.wenjin.ui.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.ui.notification.readlist.NotificationFragment;

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

    @InjectView(R.id.tv_mark_all)
    TextView mTvMarkall;

    private FragmentPagerAdapter mFragmentPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_main, container, false);
        ButterKnife.inject(this, rootView);

        mFragmentPagerAdapter = new NotificationMainAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mFragmentPagerAdapter);

        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position != 0) {
                    ((NotificationFragment)mFragmentPagerAdapter.getItem(position)).hideMarkAllView();

                }else {
                    ((NotificationFragment)mFragmentPagerAdapter.getItem(position)).showMarkAllView();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }

}
