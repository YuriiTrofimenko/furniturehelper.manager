package org.tyaa.furniturehelper.manager;

//import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.tyaa.fhelpermodel.LinkListItem;
import org.tyaa.fhelpermodel.SubLinkList;
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
import org.tyaa.furniturehelper.manager.widget.Dialog2Buttons;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class LinksEditActivity extends AppCompatActivity implements DialogInterface.OnClickListener{
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

    @BindView(R.id.subListView)
    ListView subListView;

    @BindView(R.id.linksGroupTextView)
    TextView mLinksGroupTextView;

    @BindView(R.id.etGroupName)
    EditText etGroupName;

    @BindView(R.id.llOfListView)
    LinearLayout llOfListView;

    //Кнопка добавления текстового или ссылочного прикрепления
    //после того, как его содержимое введено
    //@BindView(R.id.doAddImageView)
    //ImageView mDoAddImageView;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;
    private ActivityLinksEditBinding mActivityLinksEditBinding;

    private Long mLinksId;
    private LinkListItem mLinkListItem;
    private String mUserChosenTask;
    private String sSelectFile;

    //create new group - true, otherwise - edit group
    //private boolean createNewGroup;// пока не используется
    //text changed
    private boolean changed;//group image changed, group name changed

    private static final int REQUEST_SELECT_FILE_FOR_ITEM = 0;
    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_WEB = 2;
   // private static final int REQUEST_MAP_WEB = 3;
    private static final int REQUEST_SELECT_FILE_FOR_GROUP = 4;
    public static final String EXTRA_WEB_URL =
            "org.tyaa.furniturehelper.manager.LinksEditActivity.web_url";
    public static final String EXTRA_WEB_MAP_URL =
            "org.tyaa.furniturehelper.manager.LinksEditActivity.web_map_url";
  //  private static final String EDITETEXT_IS_VISIBLE =
   //         "org.tyaa.furniturehelper.manager.EditTextIsVisible";
  //  private static final String EDIT_MODE =
  //          "org.tyaa.furniturehelper.manager.EditMode";
    private static final String CHANGED_STATE =
            "org.tyaa.furniturehelper.manager.ChangedState";
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
/*
* 1
* 2
* 3
* 4 */
    private SelectedAttachmentType mSelectedAttachmentType;
    private Dialog2Buttons dlgYesNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_links_edit);

        Resources res = getResources();
        sSelectFile = res.getString(R.string.sSelectFile);

        if (savedInstanceState == null){Log.d("valery", "savedInstanceState == null");
            Intent intent = getIntent();

            //edit group
            if (intent.hasExtra(BusinessCardActivity.GROUP_ID)) {
                //createNewGroup = false;
                mLinksId =
                        getIntent().getLongExtra(BusinessCardActivity.GROUP_ID, 0);

                for (LinkListItem linkListItem : Global.LINK_LIST.mLinkItemList) {
                    if (linkListItem.getId() == mLinksId){
                        mLinkListItem = linkListItem;
                        break;
                    }
                }

                mActivityLinksEditBinding =
                        DataBindingUtil.setContentView(this, R.layout.activity_links_edit);
                mLinkListItem.subLinks.setLink_list_item(mLinkListItem);
                mActivityLinksEditBinding.setItems(mLinkListItem.subLinks);

                setTitle(R.string.titleEditGroup);
				
				ButterKnife.bind(this);
                mLinksGroupTextView.setText(mLinkListItem.title);
            }
            else//create new group
            {
                final String title = res.getString(R.string.titleCreateGroup);
                final String newGroupName = res.getString(R.string.sNewGroupName);
               // createNewGroup = true;
                changed = true;
                LinksGroup linksGroup =
                        Global.greenDAOFacade.createLinksGroup(
                                newGroupName
                                , true
                                , Utility.drawableToURIString(this, android.R.drawable.ic_menu_gallery)
                        );
                mLinksId = linksGroup.getId();

                mLinkListItem = new LinkListItem(
                        linksGroup.getId()
                        , linksGroup.getTitle()
                        , Utility.uriStringToDrawable(linksGroup.getDrawable())
                        , linksGroup.getChecked()
                        , new SubLinkList()
                );
                Global.LINK_LIST.mLinkItemList.add(mLinkListItem);
             //   Global.greenDAOFacade.updateLinksGroup(linksGroup);

            /*    for (LinkListItem linkListItem : Global.LINK_LIST.mLinkItemList) {
                    if (linkListItem.getId() == mLinksId){
                        mLinkListItem = linkListItem;
                        break;
                    }
                }*/

                mActivityLinksEditBinding =
                           DataBindingUtil.setContentView(this, R.layout.activity_links_edit);
                mLinkListItem.subLinks.setLink_list_item(mLinkListItem);
                mActivityLinksEditBinding.setItems(mLinkListItem.subLinks);

                setTitle(title);
				
				ButterKnife.bind(this);

                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)llOfListView.getLayoutParams();
                lp.weight = 30;

                etGroupName.setText(newGroupName);
                mLinksGroupTextView.setVisibility(View.GONE);
                etGroupName.setVisibility(View.VISIBLE);
            }
        }
        else//restore state
        {
            Log.d("valery", "restore");
            //createNewGroup = savedInstanceState.getBoolean(EDIT_MODE);
            mLinksId = savedInstanceState.getLong(BusinessCardActivity.GROUP_ID);
            changed = savedInstanceState.getBoolean(CHANGED_STATE);

            for (LinkListItem linkListItem : Global.LINK_LIST.mLinkItemList) {
                if (linkListItem.getId() == mLinksId){
                    mLinkListItem = linkListItem;
                    break;
                }
            }

            mActivityLinksEditBinding =
                    DataBindingUtil.setContentView(this, R.layout.activity_links_edit);
            mLinkListItem.subLinks.setLink_list_item(mLinkListItem);
            mActivityLinksEditBinding.setItems(mLinkListItem.subLinks);

            String groupName = savedInstanceState.getString(BusinessCardActivity.GROUP_NAME);

			ButterKnife.bind(this);

            mLinksGroupTextView.setText(groupName);
        }

        etGroupName.setHint(R.string.sEnterGroupName);

        subListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (etGroupName.getVisibility() == View.VISIBLE &&
                            etGroupName.getText().length() > 0)
                        {
                            LinearLayout.LayoutParams lp =
                                    (LinearLayout.LayoutParams)llOfListView.getLayoutParams();
                            lp.weight = 75;

                            etGroupName.setVisibility(View.GONE);

                            if (!mLinksGroupTextView.getText().equals(etGroupName.getText())) {
                                mLinksGroupTextView.setText(etGroupName.getText());
                                changed = true;
                            }

                            mLinksGroupTextView.setVisibility(View.VISIBLE);
                        }
                        break;
                }

                return false;
            }
        });

        registerForContextMenu(subListView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("valery", "save instance state");
        outState.putLong(BusinessCardActivity.GROUP_ID, mLinksId);
        //outState.putBoolean(EDIT_MODE, createNewGroup);
        outState.putBoolean(CHANGED_STATE, changed);

        if (etGroupName.getVisibility() == View.VISIBLE)
        {
            outState.putString(BusinessCardActivity.GROUP_NAME, etGroupName.getText().toString());
        }
        else
        {
            outState.putString(BusinessCardActivity.GROUP_NAME, mLinksGroupTextView.getText().toString());
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_links_edit, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.miDeleteLink:
                if (mLinkListItem.subLinks.mSubLinks.size() > 0) {
                    int index = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                    getIntent().putExtra("index", index);

                    dlgYesNo = new Dialog2Buttons();
                    Bundle b = new Bundle();
                    b.putString("msg", getResources().getString(R.string.qDeleteItem));

                    dlgYesNo.setArguments(b);

                    dlgYesNo.show(getFragmentManager(), "dlgyn");
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        dlgYesNo = null;

        if (which == DialogInterface.BUTTON_POSITIVE) {
            int index = getIntent().getIntExtra("index",-1);
            removeItem(index);
        }
    }

    private void removeItem(int index) {
        //FIXME элементы группы удаляются, но из базы данных - нет
        Object link = subListView.getAdapter().getItem(index);

        //mLinkListItem.subLinks.mSubLinks.remove(link);
        //((LinkListSubItemAdapter)subListView.getAdapter()).notifyDataSetChanged();
        //mLinkListItem.subLinks.mSubLinks.
        mLinkListItem.subLinks.mSubLinks.remove(link);
       // changed = true;
    }

    //Обработчик клика для кнопок добавления прикреплений
    //и редактирования имени группы
    @OnClick({
            R.id.addTextImageView
            , R.id.addLinkImageView
            , R.id.addMapImageView
            , R.id.addImgImageView
            , R.id.addPhotoImageView
            , R.id.doAddImageView
            , R.id.linksGroupTextView})
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
                            LinkTextItem linkTextItem = new LinkTextItem();
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
                            LinkUrlItem linkUrlItem = new LinkUrlItem();
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

                        String newMap = mInputMapTextView.getText().toString();

                        if (!newMap.equals("")) {
                            LinkMapItem linkMapItem = new LinkMapItem();
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
                      //TODO  selectImage();?
                        break;
                    }
                }
                break;
            case R.id.linksGroupTextView:
                //клик на текстовом поле - переход в режим редактирования имени группы
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)llOfListView.getLayoutParams();
                lp.weight = 30;

                mLinksGroupTextView.setVisibility(View.GONE);
                etGroupName.setText(mLinksGroupTextView.getText());
                etGroupName.setVisibility(View.VISIBLE);
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
          //  case R.id.subListView:
            //    break;
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
        builder.setTitle(R.string.sAddPhoto);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(
                        LinksEditActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE,
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                if (items[item].equals("Take Photo")) {
                    mUserChosenTask = "Take Photo";

                    if (result){
                        cameraIntent();
                    }
                }
                else if (items[item].equals("Choose from Library")) {
                    mUserChosenTask = "Choose from Library";

                    if (result){
                        galleryIntent();
                    }
                }
                else if (items[item].equals("Remove Photo")) {
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
        startActivityForResult(Intent.createChooser(intent, sSelectFile), REQUEST_SELECT_FILE_FOR_ITEM);
    }

    public void groupImgGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, sSelectFile), REQUEST_SELECT_FILE_FOR_GROUP);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mUserChosenTask.equals("Take Photo")){
                        cameraIntent();
                    }
                    else
                    if (mUserChosenTask.equals("Choose from Library")){
                        galleryIntent();
                    }
                }
                else {
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
            else
            if (requestCode == REQUEST_SELECT_FILE_FOR_GROUP){
                onSelectFromGalleryResult(data, ImageTarget.Group);
            }
            else
            if (requestCode == REQUEST_CAMERA){
                onCaptureImageResult(data);
            }
        }
        else
        if (resultCode == WebActivity.RESULT_WEB){
            mInputLinkTextView.setText(Global.currentUrl);
        }
        else
        if (resultCode == WebActivity.RESULT_MAP_WEB){
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
                return;
            }

            String uriString =
                    Utility.bitmapToUriString(
                            this
                            , bm
                            , new File(_data.getData().toString()).getName()
                    );

            if (_imageTarget == ImageTarget.Item) {
                addImgAttachment(uriString);
            }
            else
            if (_imageTarget == ImageTarget.Group) {
                changeGroupImg(bm, uriString);
            }
        }
        //Global.selectedImageView.setImageBitmap(bm);
    }

    @Override
    public void onBackPressed() {
        if (changed) {
            mLinkListItem.title = etGroupName.getText().toString();
            LinksGroup lg = Global.greenDAOFacade.getLinksGroupById(mLinksId);
            lg.setTitle(mLinkListItem.title);
            Global.greenDAOFacade.updateLinksGroup(lg);
        }

        setResult(RESULT_OK);

        super.onBackPressed();
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
