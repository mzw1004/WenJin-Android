package com.twt.service.wenjin.ui.answer;

/**
 * Created by M on 2015/4/22.
 */
public interface OnUploadCallback {

    void onUploadSuccess(String attachId);

    void onUploadFailure(String errorMsg);

}
