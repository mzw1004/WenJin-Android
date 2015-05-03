package com.twt.service.wenjin.ui.profile.follows;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.Follows;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.ResourceHelper;
import com.twt.service.wenjin.ui.BaseActivity;
import com.twt.service.wenjin.ui.common.OnItemClickListener;
import com.twt.service.wenjin.ui.profile.ProfileActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.support.v7.widget.RecyclerView.OnScrollListener;

/**
 * Created by Administrator on 2015/4/25.
 */
public class FollowsActivity extends BaseActivity implements FollowsView,OnItemClickListener{

    private static final String ACTION_TYPE_FOLLOWING = "following";
    private static final String ACTION_TYPE_FOLLOWERS = "followers";

    @Inject
    FollowsPresenter _presenter;

    @InjectView(R.id.toolbar)
    Toolbar _toolbar;

    @InjectView(R.id.profile_follows_recycler_view)
    RecyclerView _recyclerView;

    private FollowsAdapter _followsAdapter;
    private RecyclerView.OnScrollListener _onScrollListener;
    private boolean _isScrollListenerLoadingItems = false;
    private int _uid;
    private String _type;

    public static void actionStart(Context context,String type,int uid){
        Intent intent = new Intent(context,FollowsActivity.class);
        intent.putExtra("ACTION_TYPE",type);
        intent.putExtra("USER_ID",uid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follows);
        ButterKnife.inject(this);
        _type = getIntent().getStringExtra("ACTION_TYPE");
        _uid = getIntent().getIntExtra("USER_ID",0);

        if(_type.compareTo(ACTION_TYPE_FOLLOWERS) == 0){
            _toolbar.setTitle(ResourceHelper.getString(R.string.action_type_followers));
        }
        if(_type.compareTo(ACTION_TYPE_FOLLOWING) == 0){
            _toolbar.setTitle(ResourceHelper.getString(R.string.action_type_following));
        }
        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        _recyclerView.setLayoutManager(linearLayoutManager);
        _followsAdapter = new FollowsAdapter(this,_type,this);
        _recyclerView.setAdapter(_followsAdapter);
        _onScrollListener = new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                if(lastPosition == linearLayoutManager.getItemCount() -1 && dy > 0 && !_isScrollListenerLoadingItems){
                    _isScrollListenerLoadingItems = true;
                    _presenter.loadMoreItems(_type,_uid);
                }
            }
        };
        _recyclerView.setOnScrollListener(_onScrollListener);

        _presenter.loadMoreItems(_type,_uid);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_follows,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new FollowsModule(this));
    }

    @Override
    public void showMSG(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startProfileActivity(int position) {
        Follows follows = _followsAdapter.getItem(position);
        ProfileActivity.actionStart(this, follows.uid);
    }

    @Override
    public void showFooter() {
        _followsAdapter.setUseFooter(true);
    }

    @Override
    public void hideFooter() {
        _followsAdapter.setUseFooter(false);
    }

    @Override
    public void addData(List<Follows> followsList,int totalRows) {
        if(_followsAdapter.getItemCount() < totalRows){
            _followsAdapter.addData(followsList);
            _isScrollListenerLoadingItems = false;
        }else{
            showMSG(ResourceHelper.getString(R.string.no_more_information));
        }
    }

    @Override
    public void onItemClicked(View view, int position) {
        _presenter.onItemClicked(view,position);
    }
}
