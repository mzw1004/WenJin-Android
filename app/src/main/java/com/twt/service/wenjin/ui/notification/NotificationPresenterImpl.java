package com.twt.service.wenjin.ui.notification;

import android.view.View;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.bean.NotificationItem;
import com.twt.service.wenjin.bean.NotificationResponse;
import com.twt.service.wenjin.interactor.NotificationInteractor;
import com.twt.service.wenjin.support.ResourceHelper;

import java.util.List;

/**
 * Created by Green on 15-6-22.
 */
public class NotificationPresenterImpl implements NotificationPresenter , OnGetNotificationListCallback{

    private static final String LOG_TAG = NotificationPresenterImpl.class.getSimpleName();

    private NotificationView mView;
    private NotificationInteractor mInteractor;

    private int mPage = 0;
    private boolean isLoadingMore = false;
    private boolean isFirstTimeLoad = true;

    public NotificationPresenterImpl(NotificationView argView, NotificationInteractor argNotificationPresenter){

        mView = argView;
        mInteractor = argNotificationPresenter;

    }

    @Override
    public void firstTimeLoadNotificationItems() {
        mPage = 0;
        mView.useLoadMoreFooter();
        mInteractor.getNotificationList(mPage, this);
    }

    @Override
    public void refreshNotificationItems() {
        mPage = 0;
        mView.startRefresh();
        mInteractor.getNotificationList(mPage,this);

    }

    @Override
    public void loadMoreNotificationItems() {
        mPage += 1;
        isLoadingMore = true;
        mView.useLoadMoreFooter();
        mInteractor.getNotificationList(mPage,this);
    }

    @Override
    public void markNotificationAsRead(int argNotifiId) {
        mInteractor.setNotificationMarkasread(argNotifiId);
    }

    @Override
    public void onItemClick(View argView, int argPosition) {
        switch (argView.getId()){
            case R.id.iv_notifi_item_avatar:
                mView.startProfileActivity(argPosition);
                break;
            case R.id.tv_notifi_item_username:
                mView.startProfileActivity(argPosition);
                break;
            case R.id.tv_notifi_item_title:
                mView.startQuestionActivity(argPosition);
                mView.deleteItem(argPosition);
                break;
            case R.id.tv_notifi_item_content:
                mView.startAnswerActivity(argPosition);
                break;
            case R.id.tv_notifi_item_markasread:
                mView.deleteItem(argPosition);
                break;
        }
    }

    @Override
    public void onGetNotificationListSuccess(NotificationResponse argResponse) {
        mView.stopRefresh();
        if(argResponse.total_rows > 0){
            List<NotificationItem> items = argResponse.rows;
            if(isLoadingMore){
                mView.addMoreItems(items, argResponse.total_rows);
                isLoadingMore = false;
                mView.hideLoadMoreFooter();
            }else {
                mView.refreshItems(items);
                if(isFirstTimeLoad){
                    mView.hideLoadMoreFooter();
                    isFirstTimeLoad = !isFirstTimeLoad;
                }
            }
        }else {
            mView.hideLoadMoreFooter();
            mView.toastMessage(ResourceHelper.getString(R.string.no_more_information));
        }

        isLoadingMore = false;

    }

    @Override
    public void onGetNotificationListFailed(String argErrorMsg) {
        mView.stopRefresh();
        mPage -= 1;
        if(argErrorMsg != null){
            mView.toastMessage(argErrorMsg);
        }

    }
}
