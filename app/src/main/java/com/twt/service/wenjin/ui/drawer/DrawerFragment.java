package com.twt.service.wenjin.ui.drawer;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.ui.BaseFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class DrawerFragment extends BaseFragment implements DrawerView, DrawerAdapter.OnItemClickListener {

    @Inject
    DrawerPresenter mPresenter;

    @InjectView(R.id.drawer_recycler_view)
    RecyclerView mDrawerRecyclerView;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private FrameLayout mContainer;
    private DrawerAdapter mDrawerAdapter;

    public DrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
        ButterKnife.inject(this, rootView);

        mDrawerAdapter = new DrawerAdapter(this);
        mDrawerAdapter.addItem("Home");
        mDrawerAdapter.addItem("Find");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDrawerRecyclerView.setLayoutManager(linearLayoutManager);
        mDrawerRecyclerView.setAdapter(mDrawerAdapter);

        return rootView;
    }

    public void setUp(int framelayoutId, DrawerLayout drawerLayout, Toolbar toolbar) {
        mContainer = (FrameLayout) getActivity().findViewById(framelayoutId);
        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),
                mDrawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new DrawerModule(this));
    }

    @Override
    public void onItemClick(View view, int position) {
        mPresenter.select(position);
    }
}
