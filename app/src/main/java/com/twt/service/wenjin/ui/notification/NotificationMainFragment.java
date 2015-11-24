package com.twt.service.wenjin.ui.notification;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.ui.notification.readlist.NotificationFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Green on 15-6-27.
 */
public class NotificationMainFragment extends Fragment {

    @Bind(R.id.tabs_notification_main)
    TabLayout mTabLayout;

    @Bind(R.id.viewpager_notification_main)
    ViewPager mViewPager;

    @Bind(R.id.tv_mark_all)
    TextView mTvMarkall;


    private FragmentPagerAdapter mFragmentPagerAdapter;

    private boolean isFirstLoad = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notification_main, container, false);
        ButterKnife.bind(this, rootView);

        mFragmentPagerAdapter = new NotificationMainAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mFragmentPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position != 0) {
                    ((NotificationFragment) mFragmentPagerAdapter.getItem(position)).hideMarkAllView();

                } else {
                    ((NotificationFragment) mFragmentPagerAdapter.getItem(position)).showMarkAllView();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        return rootView;
    }

    @Override
    public void onStart() {
        super.onResume();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mViewPager.getCurrentItem() != 0){
            ((NotificationFragment) mFragmentPagerAdapter.getItem(1)).hideMarkAllView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
