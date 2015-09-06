package com.twt.service.wenjin.support;

import android.content.Context;
import android.content.Intent;

import com.twt.service.wenjin.ui.image.ShowWebImageActivity;

/**
 * Created by RexSun on 15/8/27.
 * */
public class JavascriptInterface {
    private Context context;

    public JavascriptInterface(Context context) {
        this.context = context;
    }


    @android.webkit.JavascriptInterface
    public void openImage(String img) {
        Intent intent = new Intent();
        intent.putExtra("image", img);
        intent.setClass(context, ShowWebImageActivity.class);
        context.startActivity(intent);
    }
}
