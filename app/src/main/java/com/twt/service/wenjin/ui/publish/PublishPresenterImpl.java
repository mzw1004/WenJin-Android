package com.twt.service.wenjin.ui.publish;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.interactor.PublishInteractor;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.MD5Utils;
import com.twt.service.wenjin.support.ResourceHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by M on 2015/4/5.
 */
public class PublishPresenterImpl implements PublishPresenter, OnPublishCallback, OnUploadCallback {

    private PublishView mPublishView;
    private PublishInteractor mInteractor;

    private String mTitle;
    private String mContent;
    private String mTopics;
    private String mAttachKey;
    private boolean mIsAnonymous;
    private List<String> mFilePath = new ArrayList<>();
    private List<String> mAttach = new ArrayList<>();

    public PublishPresenterImpl(PublishView publishView, PublishInteractor interactor) {
        this.mPublishView = publishView;
        this.mInteractor = interactor;
    }

    @Override
    public void actionPublish(String title, String content, String[] topics, boolean isAnonymous) {
        mTitle = title;
        mContent = content;
        parseTopics(topics);
        mAttachKey = MD5Utils.createAttachKey();
        System.out.println(mAttachKey);
        mIsAnonymous = isAnonymous;
        if (mFilePath.size() > 0) {
            for (int i = 0; i < mFilePath.size(); i++) {
                mInteractor.uploadFile(new File(mFilePath.get(i)), mAttachKey, this);
            }
        } else {
            publishQuestion();
        }
    }

    @Override
    public void addPath(String path) {
        if (!mFilePath.contains(path)) {
            mFilePath.add(path);
        }
    }

    private void publishQuestion() {
        if (mAttach.size() == mFilePath.size()) {
            LogHelper.d(LogHelper.makeLogTag(this.getClass()), "publsih");
            mInteractor.publishQuestion(mTitle, parseContent(mContent), mAttachKey, mTopics, mIsAnonymous, this);
        }
    }

    private String parseContent(String content) {
        for (int i = 0; i < mFilePath.size(); i++) {
            content = content.replace(mFilePath.get(i), "[attach]" + mAttach.get(i) + "[/attach]");
        }
        LogHelper.d(LogHelper.makeLogTag(this.getClass()), content);
        return content;
    }

    private void parseTopics(String[] topics) {
        mTopics = "";
        if (topics.length > 0) {
            mTopics += topics[0];
            for (int i = 1; i < topics.length; i++) {
                mTopics += topics[i];
            }
        }
    }

    @Override
    public void publishSuccess(int questionId) {
        mPublishView.toastMessage(ResourceHelper.getString(R.string.publish_success));
        mPublishView.finishActivity();
    }

    @Override
    public void publishFailure(String errorMsg) {
        mPublishView.toastMessage(errorMsg);
    }

    @Override
    public void onUploadSuccess(String attachId) {
        mAttach.add(attachId);
        publishQuestion();
    }

    @Override
    public void onUploadFailure(String errorMsg) {
        mPublishView.toastMessage(errorMsg);
    }
}
