package com.twt.service.wenjin.interactor;

import com.twt.service.wenjin.ui.profile.askanswer.OnGetAnswerCallback;
import com.twt.service.wenjin.ui.profile.askanswer.OnGetAskCallback;

/**
 * Created by Administrator on 2015/4/17.
 */
public interface ProfileAskanswerInteractor {

    /*获取我的问题列表*/
    void getAskItems(int uid,int page,int perPage,OnGetAskCallback onGetAskCallback);

    /*获取我的回答列表*/
    void getAnswerItems(int uid,int page,int perPage,OnGetAnswerCallback onGetAnswerCallback);

}
