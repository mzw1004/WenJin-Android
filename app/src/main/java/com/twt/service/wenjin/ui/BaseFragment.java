package com.twt.service.wenjin.ui;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.twt.service.wenjin.WenJinApp;

import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by M on 2015/3/20.
 */
public abstract class BaseFragment extends Fragment {

    private ObjectGraph mFragmentGraph;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragmentGraph = ((WenJinApp) activity.getApplication()).createScopedGraph(getModules().toArray());
        mFragmentGraph.inject(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    protected abstract List<Object> getModules();
}
