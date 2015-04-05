package com.twt.service.wenjin.event;

import android.graphics.Bitmap;

/**
 * Created by M on 2015/4/5.
 */
public class SelectPhotoResultEvent {

    private String photoFilePath;

    public SelectPhotoResultEvent(String photoFilePath) {
        this.photoFilePath = photoFilePath;
    }

    public String getPhotoFilePath() {
        return photoFilePath;
    }
}
