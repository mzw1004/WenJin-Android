package com.twt.service.wenjin.ui.notification;

import com.twt.service.wenjin.bean.NotificationItem;
import com.twt.service.wenjin.ui.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Green on 15-6-7.
 */
public class NotificationFragment extends BaseFragment implements NotificationView {

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(new NotificationModule(this));
    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void hideRefresh() {

    }

    @Override
    public void hideLoadMoreFooter() {

    }

    @Override
    public void useLoadMoreFooter() {

    }

    @Override
    public void toastMessage(String message) {

    }

    @Override
    public void refreshItems(ArrayList<NotificationItem> items) {

    }

    @Override
    public void loadMoreItems(ArrayList<NotificationItem> items) {

    }

    @Override
    public void startQuestionActivity(int position) {

    }

    @Override
    public void startAnswerActivity(int position) {

    }

    @Override
    public void startProfileActivity(int position) {

    }
}
