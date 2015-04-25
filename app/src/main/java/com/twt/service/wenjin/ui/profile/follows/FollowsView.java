package com.twt.service.wenjin.ui.profile.follows;

import com.twt.service.wenjin.bean.Follows;

import java.util.List;

/**
 * Created by Administrator on 2015/4/25.
 */
public interface FollowsView {
    /*
    * 显示信息
    * */
    void showMSG(String msg);

    /*
    * 打开一个新的个人信息
    * */
    void startProfileActivity(int position);

    /*
    * 显示加载更多
    * */
    void showFooter();

    /*
    * 隐藏加载更多
    * */
    void hideFooter();

    /*
    * 添加数据
    * */
    void addData(List<Follows> follows,int totalRows);
}
