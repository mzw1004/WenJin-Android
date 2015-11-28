package com.twt.service.wenjin.ui.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

/**
 * Created by M on 2015/3/28.
 */
public class PicassoImageGetter implements Html.ImageGetter {

    Resources resources;
    Picasso picasso;
    TextView textView;
    int width;

    public PicassoImageGetter(Context context, TextView textView) {
        this.textView  = textView;
        this.resources = context.getResources();
        this.picasso = Picasso.with(context);
        width = textView.getMeasuredWidth();
    }

    @Override
    public Drawable getDrawable(String source) {
        UrlDrawable urlDrawable = new UrlDrawable();

        new ImageAsyncTask(this, urlDrawable).execute(source);

        return urlDrawable;
    }

    private static class ImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<PicassoImageGetter> mGetterReference;
        private final WeakReference<UrlDrawable> mDrawableReference;

        public ImageAsyncTask(PicassoImageGetter imageGetter, UrlDrawable urlDrawable) {
            mGetterReference = new WeakReference<>(imageGetter);
            mDrawableReference = new WeakReference<>(urlDrawable);
        }

        @Override
        protected Bitmap doInBackground(String... source) {
            if (source.length == 0) {
                return null;
            }
            try {
                final PicassoImageGetter imageGetter = mGetterReference.get();
                if (imageGetter == null) {
                    return null;
                }
                return imageGetter.picasso.load(source[0]).get();
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                return;
            }
            try {
                final PicassoImageGetter imageGetter = mGetterReference.get();
                if (imageGetter == null) {
                    return;
                }
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                Bitmap b = Bitmap.createScaledBitmap(bitmap, imageGetter.width, imageGetter.width * height / width, true);
                BitmapDrawable drawable = new BitmapDrawable(imageGetter.resources, b);

                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                final UrlDrawable result = mDrawableReference.get();
                if (result == null) {
                    return;
                }
                result.setDrawable(drawable);
                result.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

                imageGetter.textView.setText(imageGetter.textView.getText());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("deprecation")
    private static class UrlDrawable extends BitmapDrawable {

        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }
    }
}
