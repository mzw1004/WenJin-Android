package com.twt.service.wenjin.ui.explore.list;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.AnswerInfo;
import com.twt.service.wenjin.bean.ExploreItem;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.ui.BaseFragment;
import com.twt.service.wenjin.ui.answer.detail.AnswerDetailActivity;
import com.twt.service.wenjin.ui.article.ArticleActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.profile.ProfileActivity;
import com.twt.service.wenjin.ui.question.QuestionActivity;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExploreListFragment extends BaseFragment implements ExploreListView,
        SwipeRefreshLayout.OnRefreshListener,OnItemClickListener{

    public final static String LOG_TAG = ExploreListFragment.class.getSimpleName();

    public final static String PARAM_TYPE = "type";
    private int type;

    @Inject
    ExploreListPresenter _exploreListPresenter;

    @Bind(R.id.explore_recycler_view)
    RecyclerView _recyclerView;

    @Bind(R.id.explore_swipe_refresh_layout)
    SwipeRefreshLayout _swipeRefreshLayout;

    private ExploreListAdapter _exploreListAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean _isFirstTimeVisibleToUser = true;

    public ExploreListFragment() {
        // Required empty public constructor
    }

    public static ExploreListFragment getInstance(int position){
        ExploreListFragment exploreListFragment = new ExploreListFragment();
        LogHelper.v(LOG_TAG,"new ExploreFragment:"+position);
        Bundle bundle = new Bundle();
        bundle.putInt(ExploreListFragment.PARAM_TYPE,position);
        exploreListFragment.setArguments(bundle);
        return exploreListFragment;
    }

    @Override
    protected List<Object> getModules(){return Arrays.<Object>asList(new ExploreListModule(this));}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt(PARAM_TYPE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_explore_list,container,false);
        LogHelper.v(LOG_TAG,"new ButterKnife injected");
        ButterKnife.bind(this, rootView);

        _swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.color_primary));
        _swipeRefreshLayout.setOnRefreshListener(this);

        _exploreListAdapter = new ExploreListAdapter(getActivity(),this);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        _recyclerView.setLayoutManager(linearLayoutManager);
        _recyclerView.setAdapter(_exploreListAdapter);

        _recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if( (lastPosition == linearLayoutManager.getItemCount() - 1 ) && dy > 0 ){
                    _exploreListPresenter.loadMoreExploreItems(type);
                }
            }
        });

        _exploreListPresenter.firstTimeLoadExploreItems(type);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    public void startRefresh() {
        if(!_swipeRefreshLayout.isRefreshing()){
            _swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void stopRefresh() {
        if(_swipeRefreshLayout.isRefreshing()){
            _swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateListData(List<ExploreItem> items) {
        _exploreListAdapter.updateData(items);
    }

    @Override
    public void addListData(List<ExploreItem> items) {
        _exploreListAdapter.addData(items);
    }

    @Override
    public void startQuestionArticlActivity(int position) {
        ExploreItem item = _exploreListAdapter.getItem(position);
        if (0 == item.post_type.compareTo("article")) {
            ArticleActivity.actionStart(getActivity(), item.id, item.title);
        }else{
            QuestionActivity.actionStart(getActivity(), item.question_id);
        }
    }

    @Override
    public void startProfileActivity(int position) {
        ExploreItem item = _exploreListAdapter.getItem(position);
        if (item.answer_users.length > 0 ) {
            ProfileActivity.actionStart(getActivity(), item.answer_users[0].uid);
        }else {
            ProfileActivity.actionStart(getActivity(),item.user_info.uid);
        }
    }

    @Override
    public void startAnswerActivity(int position) {
        ExploreItem item = _exploreListAdapter.getItem(position);
        if(item.answer_users.length > 0) {
            AnswerInfo answerInfo = item.answer_users[0];
            AnswerDetailActivity.actionStart(getActivity(), answerInfo.answer_id, answerInfo.answer_content);
        }
    }


    @Override
    public void showFooter() {
        _exploreListAdapter.setUseFooter(true);
    }

    @Override
    public void hideFooter() {
        _exploreListAdapter.setUseFooter(false);
    }


    @Override
    public void onRefresh() {
       _exploreListPresenter.loadExploreItems(type);
    }

    @Override
    public void onItemClicked(View view, int position) {
        _exploreListPresenter.onItemClicked(view,position);
    }
}
