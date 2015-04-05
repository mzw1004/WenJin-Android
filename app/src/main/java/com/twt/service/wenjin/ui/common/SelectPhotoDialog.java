package com.twt.service.wenjin.ui.common;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.otto.Produce;
import com.twt.service.wenjin.R;
import com.twt.service.wenjin.event.SelectPhotoResultEvent;
import com.twt.service.wenjin.support.BusProvider;
import com.twt.service.wenjin.support.LogHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/4/5.
 */
public class SelectPhotoDialog extends DialogFragment implements View.OnClickListener {

    private static final String LOG_TAG = SelectPhotoDialog.class.getSimpleName();

    public static final int PICK_IMAGE = 1;

    @InjectView(R.id.bt_dialog_take_photo)
    Button btTakePhoto;
    @InjectView(R.id.bt_dialog_local_photo)
    Button btLocalPhoto;

    private Bitmap photo;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .customView(R.layout.dialog_photo_select, true)
                .title(R.string.title_dialog_photo_select)
                .build();

        ButterKnife.inject(this, dialog.getCustomView());

        btTakePhoto.setOnClickListener(this);
        btLocalPhoto.setOnClickListener(this);

        return dialog;
    }

    public void show(ActionBarActivity context) {
        show(context.getSupportFragmentManager(), "PHOTO_SELECTOR");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_dialog_take_photo:
                getDialog().dismiss();
                break;
            case R.id.bt_dialog_local_photo:
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, PICK_IMAGE);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getBusInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getBusInstance().unregister(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && data != null && data.getData() != null) {
            LogHelper.v(LOG_TAG, "activity result!");
            Uri _uri = data.getData();

            //User had pick an image.
            Cursor cursor = getActivity().getContentResolver().query(_uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA}, null, null, null);
            cursor.moveToFirst();

            //Link to the image
            String imageFilePath = cursor.getString(0);
            cursor.close();

            photo = BitmapFactory.decodeFile(imageFilePath);
        }
        getDialog().dismiss();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Produce
    public SelectPhotoResultEvent producePhotoEvent() {
        return new SelectPhotoResultEvent(photo);
    }

}
