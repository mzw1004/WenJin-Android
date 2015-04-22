package com.twt.service.wenjin.ui.answer;

import com.twt.service.wenjin.R;
import com.twt.service.wenjin.interactor.AnswerInteractor;
import com.twt.service.wenjin.support.LogHelper;
import com.twt.service.wenjin.support.MD5Utils;
import com.twt.service.wenjin.support.ResourceHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by M on 2015/4/5.
 */
public class AnswerPresenterImpl implements AnswerPresenter, OnAnswerCallback, OnUploadCallback {

    private AnswerView mView;
    private AnswerInteractor mInteractor;

    private int mQuestionId;
    private String mContent;
    private String mAttachKey;
    private boolean mIsAnonymous;
    private List<String> mFilePath = new ArrayList<>();
    private List<String> mAttach = new ArrayList<>();

    public AnswerPresenterImpl(AnswerView view, AnswerInteractor interactor) {
        this.mView = view;
        this.mInteractor = interactor;
    }

    @Override
    public void actionAnswer(int questionId, String content, boolean isAnonymous) {
        mQuestionId = questionId;
        mContent = content;
        mAttachKey = MD5Utils.createAttachKey();
        System.out.println(mAttachKey);
        mIsAnonymous = isAnonymous;
        for (int i = 0; i < mFilePath.size(); i++) {
            mInteractor.uploadFile(new File(mFilePath.get(i)), mAttachKey, this);
        }
    }

    @Override
    public void addPath(String path) {
        if (!mFilePath.contains(path)) {
            mFilePath.add(path);
        }
    }

    private void publishAnswer() {
        if (mAttach.size() == mFilePath.size()) {
            mInteractor.publishAnswer(mQuestionId, parseContent(mContent), mAttachKey, mIsAnonymous, this);
        }
    }

    private String parseContent(String content) {
        for (int i = 0; i < mFilePath.size(); i++) {
            content = content.replace(mFilePath.get(i), "[attach]" + mAttach.get(i) + "[/attach]");
        }
        LogHelper.d(LogHelper.makeLogTag(this.getClass()), content);
        return content;
    }

    @Override
    public void onAnswerSuccess(int answerId) {
        mView.toastMessage(ResourceHelper.getString(R.string.publish_success));
        mView.finishActivity();
    }

    @Override
    public void onAnswerFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }

    @Override
    public void onUploadSuccess(String attachId) {
        mAttach.add(attachId);
        publishAnswer();
    }

    @Override
    public void onUploadFailure(String errorMsg) {
        mView.toastMessage(errorMsg);
    }
}
