package com.twt.service.wenjin.ui.common;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.twt.service.wenjin.support.ResourceHelper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by M on 2015/4/5.
 */
public class SelectPhotoDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String LOG_TAG = SelectPhotoDialogFragment.class.getSimpleName();

    public static final int PICK_IMAGE_REQUEST_CODE = 1;
    public static final int CAPTURE_IMAGE_REQUEST_CODE = 2;

    @InjectView(R.id.bt_dialog_take_photo)
    Button btTakePhoto;
    @InjectView(R.id.bt_dialog_local_photo)
    Button btLocalPhoto;

    private String photoFileName;
    private String photoFilePath;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .customView(R.layout.dialog_photo_select, true)
//                .title(R.string.title_dialog_photo_select)
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
        Intent intent;
        switch (v.getId()) {
            case R.id.bt_dialog_take_photo:
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                photoFileName = getPhotoFileName();
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
                startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE);
                break;
            case R.id.bt_dialog_local_photo:
                intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
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
        switch (requestCode) {
            case PICK_IMAGE_REQUEST_CODE:
                if (data != null && data.getData() != null) {
                    Uri _uri = data.getData();
                    //User had pick an image.
                    Cursor cursor = getActivity().getContentResolver().query(_uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA}, null, null, null);
                    cursor.moveToFirst();
                    //Link to the image
                    photoFilePath = cursor.getString(0);
                    cursor.close();
                }
                break;
            case CAPTURE_IMAGE_REQUEST_CODE:
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                photoFilePath = takenPhotoUri.getPath();
                break;
        }
        getDialog().dismiss();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Produce
    public SelectPhotoResultEvent producePhotoEvent() {
        return new SelectPhotoResultEvent(photoFilePath);
    }

    public Uri getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ResourceHelper.getString(R.string.app_name));

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            LogHelper.d(LOG_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

}
