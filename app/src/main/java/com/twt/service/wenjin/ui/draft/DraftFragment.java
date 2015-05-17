package com.twt.service.wenjin.ui.draft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.ui.BaseFragment;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/5/17.
 */
public class DraftFragment extends BaseFragment implements DraftView {

    @Inject
    DraftPresenter mPresenter;

    @InjectView(R.id.recycler_view_draft)
    RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_draft, container, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new DraftModule(this));
    }
}
