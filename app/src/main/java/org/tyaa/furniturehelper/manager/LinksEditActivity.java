package org.tyaa.furniturehelper.manager;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.tyaa.furniturehelper.manager.adapter.EntitiesModelsAdapter;
import org.tyaa.furniturehelper.manager.common.Generator;
import org.tyaa.furniturehelper.manager.common.Global;
import org.tyaa.furniturehelper.manager.common.Utility;
import org.tyaa.furniturehelper.manager.databinding.ActivityLinksEditBinding;
import org.tyaa.furniturehelper.manager.entity.LinkImgItem;
import org.tyaa.furniturehelper.manager.entity.LinkMapItem;
import org.tyaa.furniturehelper.manager.entity.LinkTextItem;
import org.tyaa.furniturehelper.manager.entity.LinkUrlItem;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class LinksEditActivity extends AppCompatActivity {

    //Картинка группы ссылок
    @BindView(R.id.linksGroupImageButton)
    ImageButton mLinksGroupImageButton;

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

    private ActivityLinksEditBinding mActivityLinksEditBinding;

    private Long mLinksId;
    private LinkListItem mLinkListItem;
    private String mUserChosenTask;

    private static final int REQUEST_SELECT_FILE_FOR_ITEM = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_WEB = 2;
    private static final int REQUEST_MAP_WEB = 3;
    private static final int REQUEST_SELECT_FILE_FOR_GROUP = 4;
    public static final String EXTRA_WEB_URL =
            "org.tyaa.furniturehelper.manager.LinksEditActivity.web_url";
    public static final String EXTRA_WEB_MAP_URL =
            "org.tyaa.furniturehelper.manager.LinksEditActivity.web_map_url";

    private enum SelectedAttachmentType {

        Empty
        , Text
        , Link
        , Map
        , Image
        , Camera
    }

    private enum ImageTarget {

        Item
        , Group
    }

    private SelectedAttachmentType mSelectedAttachmentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_links_edit);

        mLinksId =
                getIntent().getLongExtra(BusinessCardActivity.SELECTED_LINK_LIST_ITEM_TITLE, 0);
                //getIntent().getStringExtra(BusinessCardActivity.SELECTED_LINK_LIST_ITEM_TITLE);

        for (LinkListItem linkListItem : Global.LINK_LIST.mLinkItemList) {

            //
            if (linkListItem.getId() == mLinksId){

                mLinkListItem = linkListItem;
            }
        }

        //mLinkListItem = Global.LINK_LIST.mLinkItemList.get(mLinksPos);

        mActivityLinksEditBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_links_edit);
        mLinkListItem.subLinks.setLink_list_item(mLinkListItem);
        mActivityLinksEditBinding.setItems(mLinkListItem.subLinks);
        //activityLinksEditBinding.setLink_list_item(mLinkListItem);
        ButterKnife.bind(this);
    }

    //Обработчик клика для кнопок добавления прикреплений
    @OnClick({
            R.id.addTextImageView
            , R.id.addLinkImageView
            , R.id.addMapImageView
            , R.id.addImgImageView
            , R.id.addPhotoImageView
            , R.id.doAddImageView})
    void onClick(View view) {

        switch (view.getId()) {

            case R.id.addTextImageView:
                // Кнопка для начала процесса добавления текстового прикрепления,
                //после ее нажатия появляется поле ввода текста и кнопка "+" для его добавления
                mSelectedAttachmentType = SelectedAttachmentType.Text;
                mAttIconsLinearLayout.setVisibility(View.GONE);
                mInputsLinearLayout.setVisibility(View.VISIBLE);
                mInputTextTextView.setVisibility(View.VISIBLE);
                //Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();
                break;
            case R.id.addLinkImageView:
                // Кнопка добавления ссылки из браузера
                mSelectedAttachmentType = SelectedAttachmentType.Link;
                mAttIconsLinearLayout.setVisibility(View.GONE);
                mInputsLinearLayout.setVisibility(View.VISIBLE);
                mInputLinkTextView.setVisibility(View.VISIBLE);

                String urlString = "http://www.google.com";
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse(url));
                Intent webIntent = new Intent(this, WebActivity.class);
                webIntent.putExtra(EXTRA_WEB_URL, urlString);
                //startActivity(webIntent);
                startActivityForResult(webIntent, REQUEST_WEB);
                break;
            case R.id.addMapImageView:
                // Кнопка добавления ссылки на карту из браузера
                mSelectedAttachmentType = SelectedAttachmentType.Map;
                mAttIconsLinearLayout.setVisibility(View.GONE);
                mInputsLinearLayout.setVisibility(View.VISIBLE);
                mInputMapTextView.setVisibility(View.VISIBLE);

                String urlMapString = "https://www.google.com/maps";
                //Intent webMapIntent = new Intent(this, WebActivity.class);
                Intent webMapIntent = new Intent(Intent.ACTION_VIEW);
                //webMapIntent.putExtra(EXTRA_WEB_MAP_URL, urlMapString);
                webMapIntent.setData(Uri.parse(urlMapString));
                //startActivityForResult(webMapIntent, REQUEST_MAP_WEB);
                startActivity(webMapIntent);
                break;
            case R.id.addImgImageView:
                // Кнопка добавления изображения из галлереи
                mSelectedAttachmentType = SelectedAttachmentType.Image;
                galleryIntent();
                break;
            case R.id.addPhotoImageView:
                // Кнопка добавления изображения, получаемого от камеры
                mSelectedAttachmentType = SelectedAttachmentType.Image;
                cameraIntent();
                break;
            case R.id.doAddImageView:
                // Кнопка "+", которую нужно кликнуть после ввода текста
                //или вставки ссылки / ссылки на карту, чтобы добавить прикрепление

                switch (mSelectedAttachmentType) {

                    case Text: {

                        mAttIconsLinearLayout.setVisibility(View.VISIBLE);
                        mInputsLinearLayout.setVisibility(View.GONE);
                        mInputTextTextView.setVisibility(View.GONE);

                        String newText =
                                mInputTextTextView.getText().toString();
                        if (!newText.equals("")) {

                            LinkTextItem linkTextItem =
                                    new LinkTextItem();
                            linkTextItem.setText(newText);
                            linkTextItem.setGuid(Generator.generateGuid());
                            Global.greenDAOFacade.createLink(linkTextItem, mLinksId);
                            mLinkListItem.subLinks.mSubLinks.add(
                                    EntitiesModelsAdapter.linkItemToSubLink(linkTextItem)
                            );
                            mInputTextTextView.setText("");
                        }
                        break;
                    }
                    case Link: {

                        mAttIconsLinearLayout.setVisibility(View.VISIBLE);
                        mInputsLinearLayout.setVisibility(View.GONE);
                        mInputLinkTextView.setVisibility(View.GONE);

                        String newLink =
                                mInputLinkTextView.getText().toString();
                        if (!newLink.equals("")) {

                            LinkUrlItem linkUrlItem =
                                    new LinkUrlItem();
                            linkUrlItem.setLink(newLink);
                            linkUrlItem.setGuid(Generator.generateGuid());
                            Global.greenDAOFacade.createLink(linkUrlItem, mLinksId);
                            mLinkListItem.subLinks.mSubLinks.add(
                                    EntitiesModelsAdapter.linkItemToSubLink(linkUrlItem)
                            );
                            mInputLinkTextView.setText("");
                        }
                        break;
                    }
                    case Map: {

                        mAttIconsLinearLayout.setVisibility(View.VISIBLE);
                        mInputsLinearLayout.setVisibility(View.GONE);
                        mInputMapTextView.setVisibility(View.GONE);

                        String newMap =
                                mInputMapTextView.getText().toString();
                        if (!newMap.equals("")) {

                            LinkMapItem linkMapItem =
                                    new LinkMapItem();
                            linkMapItem.setLink(newMap);
                            linkMapItem.setGuid(Generator.generateGuid());
                            Global.greenDAOFacade.createLink(linkMapItem, mLinksId);
                            mLinkListItem.subLinks.mSubLinks.add(
                                    EntitiesModelsAdapter.linkItemToSubLink(linkMapItem)
                            );
                            mInputMapTextView.setText("");
                        }
                        break;
                    }
                    case Image: {

                        break;
                    }
                }
                break;
        }
    }

    //Обработчик клика для изменения картинки и заголовка группы
    @OnLongClick({
            R.id.linksGroupImageButton
            })
    boolean onLongClick(View view) {

        switch (view.getId()) {

            case R.id.linksGroupImageButton:
                //
                groupImgGalleryIntent();
                break;
        }
        return true;
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
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_SELECT_FILE_FOR_ITEM);
    }

    public void groupImgGalleryIntent() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_SELECT_FILE_FOR_GROUP);
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

            if (requestCode == REQUEST_SELECT_FILE_FOR_ITEM){

                onSelectFromGalleryResult(data, ImageTarget.Item);
            }
            else if (requestCode == REQUEST_SELECT_FILE_FOR_GROUP){

                onSelectFromGalleryResult(data, ImageTarget.Group);
            }
            else if (requestCode == REQUEST_CAMERA){

                onCaptureImageResult(data);
            }
        } else if (resultCode == WebActivity.RESULT_WEB){

            mInputLinkTextView.setText(Global.currentUrl);
        } else if (resultCode == WebActivity.RESULT_MAP_WEB){

            mInputMapTextView.setText(Global.currentUrl);
        }
    }

    /**
     * Обработчик выбора изображения из галлереи
     * */
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent _data, ImageTarget _imageTarget) {

        Bitmap bm = null;
        if (_data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(
                        getApplicationContext().getContentResolver()
                        , _data.getData()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            String uriString =
                    Utility.bitmapToUriString(
                            this
                            , bm
                            , new File(_data.getData().toString()).getName()
                    );

            if (_imageTarget == ImageTarget.Item) {

                addImgAttachment(uriString);
            } else if (_imageTarget == ImageTarget.Group) {

                changeGroupImg(bm, uriString);
            }
        }
        //Global.selectedImageView.setImageBitmap(bm);
    }

    /**
     * Обработчик получения изображения с камеры
     * */
    private void onCaptureImageResult(Intent data) {

        Bitmap bm = (Bitmap) data.getExtras().get("data");
        String uriString =
                Utility.bitmapToUriString(
                        this
                        , bm
                        , System.currentTimeMillis() + ""
                );
        addImgAttachment(uriString);

        /*ByteArrayOutputStream bytes = new ByteArrayOutputStream();
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
        }*/
        //Global.selectedImageView.setImageBitmap(thumbnail);
    }

    private void addImgAttachment(String _uriString){

        LinkImgItem linkImgItem = new LinkImgItem();
        linkImgItem.setDrawable(_uriString);
        linkImgItem.setGuid(Generator.generateGuid());
        Global.greenDAOFacade.createLink(linkImgItem, mLinksId);
        mLinkListItem.subLinks.mSubLinks.add(
                EntitiesModelsAdapter.linkItemToSubLink(linkImgItem)
        );
    }

    private void changeGroupImg(Bitmap _bitmap, String _uriString){

        //Global.currentGroupImageButton.setImageBitmap(_bitmap);

        //mLinksGroupImageButton.setImageBitmap(_bitmap);

        mLinkListItem.drawable = Utility.uriStringToDrawable(_uriString);
        //mActivityLinksEditBinding.notifyChange();
        //mActivityLinksEditBinding.executePendingBindings();
        mActivityLinksEditBinding.invalidateAll();

        LinksGroup linksGroup = null;

        for (LinksGroup _linksGroup : Global.linksGroupList) {

            //Log.d("asd", _linksGroup.getId() + " " + mLinksId);
            //
            if (_linksGroup.getId() == mLinksId){

                linksGroup = _linksGroup;
            }
        }

        Log.d("asd1", linksGroup.getDrawable());
        linksGroup.setDrawable(_uriString);

        if (linksGroup != null) {

            Log.d("asd2", linksGroup.getDrawable());
            Global.greenDAOFacade.updateLinksGroup(linksGroup);
        }
        /*mLinkListItem.subLinks.mSubLinks.add(
                EntitiesModelsAdapter.linkItemToSubLink(linkImgItem)
        );*/
    }
}
