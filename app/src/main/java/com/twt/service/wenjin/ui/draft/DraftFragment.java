package com.twt.service.wenjin.ui.draft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.AnswerDraft;
import com.twt.service.wenjin.ui.BaseFragment;
import com.twt.service.wenjin.ui.answer.AnswerActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by M on 2015/5/17.
 */
public class DraftFragment extends BaseFragment implements DraftView, OnItemClickListener {

    @Inject
    DraftPresenter mPresenter;

    @Bind(R.id.recycler_view_draft)
    RecyclerView mRecyclerView;
    @Bind(R.id.tv_draft_hint)
    TextView tvHint;

    private DraftAdapter mAdapter;
    private MenuItem mActionDelete;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_draft, container, false);
        ButterKnife.bind(this, rootView);

        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getDrafts();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_draft, menu);
        mActionDelete = menu.findItem(R.id.action_delete);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                if (mAdapter.isDeleteMode()) {
                    mAdapter.setDeleteMode(false);
                    mActionDelete.setIcon(R.drawable.ic_action_delete);
                } else {
                    mAdapter.setDeleteMode(true);
                    mActionDelete.setIcon(R.drawable.ic_action_done);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new DraftModule(this));
    }

    @Override
    public void showHintText() {
        tvHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHintText() {
        tvHint.setVisibility(View.GONE);
    }

    @Override
    public void showRecyclerView(List<AnswerDraft> drafts) {
        mAdapter = new DraftAdapter((ArrayList<AnswerDraft>) drafts, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClicked(View view, int position) {
        if (mAdapter.isDeleteMode()) {
            mAdapter.deleteAnswerDraft(position);
        } else {
            AnswerDraft draft = mAdapter.getAnswerDraft(position);
            AnswerActivity.actionStart(getActivity(), draft);
            mAdapter.deleteAnswerDraft(position);
        }
    }
}
