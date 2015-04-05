package com.twt.service.wenjin.event;

import android.graphics.Bitmap;

/**
 * Created by M on 2015/4/5.
 */
public class SelectPhotoResultEvent {

    private Bitmap photo;

    public SelectPhotoResultEvent(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return photo;
    }

}
