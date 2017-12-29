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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.tyaa.furniturehelper.manager.adapter.EntitiesModelsAdapter;
import org.tyaa.furniturehelper.manager.common.Global;
import org.tyaa.furniturehelper.manager.common.Utility;
import org.tyaa.furniturehelper.manager.databinding.ActivityLinksEditBinding;
import org.tyaa.furniturehelper.manager.entity.LinkTextItem;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LinksEditActivity extends AppCompatActivity {

    //Панель кнопок добавления прикреплений
    @BindView(R.id.attIconsLinearLayout)
    LinearLayout mAttIconsLinearLayout;

    //Панель полей ввода содержимого прикреплений
    @BindView(R.id.inputsLinearLayout)
    LinearLayout mInputsLinearLayout;

    //Поле ввода содержимого текстового прикрепления
    @BindView(R.id.inputTextTextView)
    EditText mInputTextTextView;
    //Поле ввода содержимого ссылочного прикрепления
    @BindView(R.id.inputLinkTextView)
    EditText mInputLinkTextView;
    //Поле ввода содержимого прикрепления карты
    @BindView(R.id.inputMapTextView)
    EditText mInputMapTextView;

    //Кнопка добавления текстового или ссылочного прикрепления
    //после того, как его содержимое введено
    //@BindView(R.id.doAddImageView)
    //ImageView mDoAddImageView;

    private Long mLinksId;
    private LinkListItem mLinkListItem;
    private String mUserChosenTask;

    private static final int SELECT_FILE = 0;
    private static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_links_edit);

        //Toast.makeText(this, String.valueOf(mAttIconsLinearLayout.getVisibility()), Toast.LENGTH_SHORT).show();

        mLinksId =
                getIntent().getLongExtra(BusinessCardActivity.SELECTED_LINK_LIST_ITEM_TITLE, 0);
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
        ButterKnife.bind(this);
    }

    //Обработчик клика для кнопок добавления прикреплений
    @OnClick({
            R.id.addTextImageView
            , R.id.addLinkImageView
            , R.id.addMapImageView
            , R.id.doAddImageView})
    void onClick(View view) {

        switch (view.getId()) {

            case R.id.addTextImageView:
                // ...
                mAttIconsLinearLayout.setVisibility(View.GONE);
                mInputsLinearLayout.setVisibility(View.VISIBLE);
                mInputTextTextView.setVisibility(View.VISIBLE);
                //Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addLinkImageView:
                // ...
                break;
            case R.id.addMapImageView:
                // ...
                break;
            case R.id.doAddImageView:
                // ...
                mAttIconsLinearLayout.setVisibility(View.VISIBLE);
                mInputsLinearLayout.setVisibility(View.GONE);
                mInputTextTextView.setVisibility(View.GONE);

                String newText =
                        mInputTextTextView.getText().toString();
                Log.d("asd3", newText);
                if (!newText.equals("")){

                    Log.d("asd4", "yes");
                    LinkTextItem linkTextItem =
                            new LinkTextItem();
                    linkTextItem.setText(newText);
                    Global.greenDAOFacade.createLink(linkTextItem, mLinksId);
                    mLinkListItem.subLinks.mSubLinks.add(
                            EntitiesModelsAdapter.linkItemToSubLink(linkTextItem)
                    );
                }

                break;
        }
    }

    public void selectImage() {

        final CharSequence[] items = {
                "Take Photo"
                , "Choose from Library"
                , "Remove Photo"
                , "Remove Item"
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
                } else if (items[item].equals("Remove Photo")) {

                    Global.selectedImageView.setImageDrawable(Global.EMPTY_DRAWABLE);
                }
                else if (items[item].equals("Remove Item")) {

                    mLinkListItem.subLinks.mSubLinks.remove(Global.selectedSubLinkPos);
                }
                else if (items[item].equals("Cancel")) {

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

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Global.selectedImageView.setImageBitmap(bm);
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
        Global.selectedImageView.setImageBitmap(thumbnail);
    }
}
