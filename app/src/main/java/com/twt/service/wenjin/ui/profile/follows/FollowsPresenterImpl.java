package com.twt.service.wenjin.ui.profile.follows;

import android.view.View;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.Follows;
import com.twt.service.wenjin.bean.FollowsResponse;
import com.twt.service.wenjin.interactor.FollowsInteractor;
import com.twt.service.wenjin.support.ResourceHelper;

import java.util.List;

/**
 * Created by Administrator on 2015/4/25.
 */
public class FollowsPresenterImpl implements FollowsPresenter,OnGetFollowCallback {

    private static final String ACTION_TYPE_FOLLOWING = "following";
    private static final String ACTION_TYPE_FOLLOWERS = "followers";

    private FollowsView _followsView;
    private FollowsInteractor _interactor;

    private boolean _isLoadMore = false;
    private int _page = 1;

    public FollowsPresenterImpl(FollowsView followsView,FollowsInteractor followsInteractor){
        _followsView = followsView;
        _interactor = followsInteractor;
    }

    @Override
    public void onGetFollowersSuccess(FollowsResponse response) {
        _page += 1;
        if(response.total_rows > 0){
            List<Follows> followsList = response.rows;
            if(_isLoadMore){
                _followsView.addData(followsList, response.total_rows);
                _isLoadMore = false;
            }else{
            }
        }else{
            _followsView.showMSG(ResourceHelper.getString(R.string.no_more_information));
        }
        _isLoadMore = false;
        _followsView.hideFooter();
    }

    @Override
    public void onGetFollowersFailed(String errorMsg) {
        _page -= 1;
        _followsView.showMSG(errorMsg);
        _isLoadMore = false;
    }

    @Override
    public void onItemClicked(View v, int position) {
        _followsView.startProfileActivity(position);
    }

    @Override
    public void loadMoreItems(String type, int uid) {
        if(_isLoadMore){return;}
        _isLoadMore = true;
        _followsView.showFooter();
        getItems(type,uid);
    }

    private void getItems(String type,int uid){
        if(type.compareTo(ACTION_TYPE_FOLLOWERS) == 0){
            _interactor.getFollowersItems(uid, _page, 10, this);
        }

        if(type.compareTo(ACTION_TYPE_FOLLOWING) == 0 ){
            _interactor.getFollowingItems(uid,_page,10,this);
        }
    }
}
