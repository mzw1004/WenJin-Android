package com.twt.service.wenjin.ui.profile.askanswer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2015/4/17.
 */
public interface ProfileAskanswerView {

    void toastMessage(String msg);

    void refreshListData(Object items);

    void addListData(Object items,int totalRos);

    void startQuestionActivity(int position);

    void startAnswerActivity(int position);

    void showFooter();

    void hideFooter();

}
