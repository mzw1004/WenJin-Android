package com.twt.service.wenjin.ui.notification;

import android.view.View;

import com.twt.service.wenjin.bean.NotificationItem;
import com.twt.service.wenjin.interactor.NotificationInteractor;

import java.util.List;

/**
 * Created by Green on 15-6-22.
 */
public class NotificationPresenterImpl implements NotificationPresenter , OnGetNotificationListCallback{


    private NotificationView mView;
    private NotificationInteractor mInteractor;

    public NotificationPresenterImpl(NotificationView argView, NotificationInteractor argNotificationPresenter){

        mView = argView;
        mInteractor = argNotificationPresenter;

    }

    @Override
    public void refreshNotificationItems() {

    }

    @Override
    public void loadMoreNotificationItems() {

    }

    @Override
    public void onItemClick(View argView, int argPosition) {

    }

    @Override
    public void onGetNotificationListSuccess(List<NotificationItem> itemList) {

    }

    @Override
    public void onGetNotificationListFailed(String argErrorMsg) {

    }
}
