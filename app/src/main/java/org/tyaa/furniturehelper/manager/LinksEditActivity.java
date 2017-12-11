package org.tyaa.furniturehelper.manager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.tyaa.furniturehelper.manager.common.Global;
import org.tyaa.furniturehelper.manager.common.Utility;
import org.tyaa.furniturehelper.manager.databinding.ActivityLinksEditBinding;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class LinksEditActivity extends AppCompatActivity {

    private int mLinksId;
    private LinkListItem mLinkListItem;
    private String mUserChosenTask;

    private static final int SELECT_FILE = 0;
    private static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_links_edit);

        mLinksId =
                getIntent().getIntExtra(BusinessCardActivity.SELECTED_LINK_LIST_ITEM_TITLE, 0);
                //getIntent().getStringExtra(BusinessCardActivity.SELECTED_LINK_LIST_ITEM_TITLE);

        //Log.d("asd", String.valueOf(mLinksPos));

        for (LinkListItem linkListItem : Global.LINK_LIST.mLinkItemList) {

            //Log.d("asd2", linkListItem.title);
            if (linkListItem.getId() == mLinksId){

                mLinkListItem = linkListItem;
            }
        }

        //mLinkListItem = Global.LINK_LIST.mLinkItemList.get(mLinksPos);

        //Log.d("asd2", mLinkListItem.title);
        ActivityLinksEditBinding activityLinksEditBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_links_edit);
        mLinkListItem.subLinks.setLink_list_item(mLinkListItem);
        activityLinksEditBinding.setItems(mLinkListItem.subLinks);
        //activityLinksEditBinding.setLink_list_item(mLinkListItem);
    }

    private void selectImage() {

        final CharSequence[] items = {
                "Take Photo"
                , "Choose from Library"
                , "Cancel"
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(LinksEditActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                boolean result = Utility.checkPermission(LinksEditActivity.this);
                if (items[item].equals("Take Photo")) {

                    mUserChosenTask = "Take Photo";
                    if(result){

                        cameraIntent();
                    }
                } else if (items[item].equals("Choose from Library")) {

                    mUserChosenTask = "Choose from Library";
                    if(result){

                        galleryIntent();
                    }
                } else if (items[item].equals("Cancel")) {

                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if(mUserChosenTask.equals("Take Photo")){

                        cameraIntent();
                    }
                    else if(mUserChosenTask.equals("Choose from Library")){

                        galleryIntent();
                    }
                } else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_FILE){

                onSelectFromGalleryResult(data);
            }
            else if (requestCode == REQUEST_CAMERA){

                onCaptureImageResult(data);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //ivImage.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ivImage.setImageBitmap(thumbnail);
    }
}
