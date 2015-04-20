package com.twt.service.wenjin.support;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.twt.service.wenjin.WenJinApp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Created by M on 2015/3/22.
 */
public class ResourceHelper {

    public static int getColor(int colorId) {
        return WenJinApp.getContext().getResources().getColor(colorId);
    }

    public static Drawable getDrawable(int drawableId) {
        return WenJinApp.getContext().getResources().getDrawable(drawableId);
    }

    public static String getString(int stringId) {
        return WenJinApp.getContext().getResources().getString(stringId);
    }

    public static String[] getStringArrays(int arrayId) {
        return WenJinApp.getContext().getResources().getStringArray(arrayId);
    }

    public static Bitmap readBitmapAutoSize(String filePath, int outWidth, int outHeight) {
        // outWidth和outHeight是目标图片的最大宽度和高度，用作限制
        FileInputStream fs = null;
        BufferedInputStream bs = null;
        try {
            fs = new FileInputStream(filePath);
            bs = new BufferedInputStream(fs);
            BitmapFactory.Options options = setBitmapOption(filePath, outWidth, outHeight);
            return BitmapFactory.decodeStream(bs, null, options);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bs != null) {
                    bs.close();
                }
                if (fs  != null) {
                    fs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static BitmapFactory.Options setBitmapOption(String file, int width, int height) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inJustDecodeBounds = true;
        // 设置只是解码图片的边距，此操作目的是度量图片的实际宽度和高度
        BitmapFactory.decodeFile(file, opt);

        int outWidth = opt.outWidth; // 获得图片的实际高和宽
        int outHeight = opt.outHeight;
        opt.inDither = false;
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        // 设置加载图片的颜色数为16bit，默认是RGB_8888，表示24bit颜色和透明通道，但一般用不上
        opt.inSampleSize = 1;
        // 设置缩放比,1表示原比例，2表示原来的四分之一....
        // 计算缩放比
        int sampleSize;
        if (outWidth != 0 && outHeight != 0 && width != 0 && height != 0) {
            sampleSize = (outWidth / width + outHeight / height) / 2;
            opt.inSampleSize = sampleSize;
        }

        opt.inJustDecodeBounds = false;// 最后把标志复原
        return opt;
    }
}
