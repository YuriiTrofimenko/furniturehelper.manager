package org.tyaa.furniturehelper.manager;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.tyaa.fhelpermodel.LinkList;
import org.tyaa.fhelpermodel.LinkListItem;
import org.tyaa.furniturehelper.manager.adapter.LinkListItemAdapter;
import org.tyaa.furniturehelper.manager.common.Global;
import org.tyaa.furniturehelper.manager.common.Utility;
import org.tyaa.furniturehelper.manager.databinding.ActivityBusinessCardBinding;
import org.tyaa.furniturehelper.manager.entity.LinkTextItem;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.entity.interfaces.ILinkItem;
import org.tyaa.furniturehelper.manager.receiver.CallReceiver;
import org.tyaa.furniturehelper.manager.widget.Dialog2Buttons;
import org.tyaa.furnituresender.messagehelper.MessageHelper;
import org.tyaa.furnituresender.messagehelper.ProviderData;
import org.tyaa.furnituresender.messageproviders.SMSProvider;
import org.tyaa.furnituresender.messageproviders.SMSProviderOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessCardActivity extends AppCompatActivity implements DialogInterface.OnClickListener{
    //@BindViews({R.id.viberTabLayout, R.id.whatsappTabLayout, R.id.telegramTabLayout, R.id.smsTabLayout})
    //List<View> mTabs;

    //Кнопка-картинка выхода из главной эктивити приложения
    @BindView(R.id.exitImageButton)
    ImageButton mExitImageButton;

    //Кнопка-картинка добавления новой группы ссылок
    @BindView(R.id.addGroupImageButton)
    ImageButton mAddGroupImageButton;

    //Кнопка-картинка отправки всех групп ссылок
    @BindView(R.id.sendImageButton)
    ImageButton mSendImageButton;

    private LinearLayout mViberTabLayout;
    private LinearLayout mWhatsappTabLayout;
    private LinearLayout mTelegramTabLayout;
    private LinearLayout mSmsTabLayout;

    private final List<LinearLayout> mTabLayouts = new ArrayList<>();
    private MessageHelper messageHelper;

    private ImageView mViberTabImageView;
    private ImageView mWhatsappTabImageView;
    private ImageView mTelegramTabImageView;
    private ImageView mSmsTabImageView;
    private SMSProvider smsProvider;
    private final List<ImageView> mTabImageViews = new ArrayList<>();
    private Dialog2Buttons dlgYesNo;
    private ListView mLinkListView;

    private String mPhoneNumber = "";

    private enum Provider {
        Viber
        , Whatsapp
        , Telegram
        , Sms
    }
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private final Map<Integer, Provider> mProvidersSet = new HashMap<>();

    private Provider mSelectedProvider = Provider.Sms;

    private ActivityBusinessCardBinding mActivityBusinessCardBinding;

    public static final String GROUP_ID =
            "org.tyaa.furniturehelper.manager.AppCompatActivity.SELECTED_TITLE";
    public static final String GROUP_NAME =
            "org.tyaa.furniturehelper.manager.AppCompatActivity.GroupName";
    public static final String STATE_PHONE_NUMBER =
            "org.tyaa.furniturehelper.manager.AppCompatActivity.PHONE_NUMBER";

    public static final int EDIT_GROUP_REQUEST = 0;
    public static final int CONTACTS_REQUEST = 1;

    /*@OnClick({R.id.viberTab, R.id.whatsappTab, R.id.telegramTab, R.id.smsTab})
    void onClickTab(View selectedView) {

        for (View item : mTabsList) {

            Log.i("asd", item.toString());
        }
        ButterKnife.apply(
                mTabsList
                , (view, value, index) ->
                        view.setBackgroundColor(value)
                , android.R.color.transparent
        );
        Log.i("asd", selectedView.getBackground().toString());
        selectedView.setBackgroundColor(Color.parseColor("#CCCCCC"));
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermission();

        boolean canWrite = Utility.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        // recovering the instance state
        if (savedInstanceState != null) {
            mPhoneNumber = savedInstanceState.getString(STATE_PHONE_NUMBER);
        }

        mProvidersSet.put(0, Provider.Viber);
        mProvidersSet.put(1, Provider.Whatsapp);
        mProvidersSet.put(2, Provider.Telegram);
        mProvidersSet.put(3, Provider.Sms);

        setContentView(R.layout.activity_business_card);

        Intent incomingIntent = getIntent();

        if (CallReceiver.isReceiverCalled()) {
            String message;

            if (!mPhoneNumber.equals("")) {
                message = getResources().getString(R.string.sPhoneIs) + " " + mPhoneNumber;
            } else if (incomingIntent.hasExtra(CallReceiver.EXTRA_PHONE_NUMBER)) {
                mPhoneNumber = getIntent().getStringExtra(CallReceiver.EXTRA_PHONE_NUMBER);
                message = mPhoneNumber;
            } else {
                message = getResources().getString(R.string.sPhoneIsNoSet);
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }

        if (canWrite) {
            bindData();
        }

        //activityBusinessCardBinding.
        mViberTabLayout = (LinearLayout) findViewById(R.id.viberTabLayout);
        mWhatsappTabLayout = (LinearLayout) findViewById(R.id.whatsappTabLayout);
        mTelegramTabLayout = (LinearLayout) findViewById(R.id.telegramTabLayout);
        mSmsTabLayout = (LinearLayout) findViewById(R.id.smsTabLayout);

        mTabLayouts.add(mViberTabLayout);
        mTabLayouts.add(mWhatsappTabLayout);
        mTabLayouts.add(mTelegramTabLayout);
        mTabLayouts.add(mSmsTabLayout);

        mViberTabImageView = (ImageView) findViewById(R.id.viberTabImageView);
        mWhatsappTabImageView = (ImageView) findViewById(R.id.whatsappTabImageView);
        mTelegramTabImageView = (ImageView) findViewById(R.id.telegramTabImageView);
        mSmsTabImageView = (ImageView) findViewById(R.id.smsTabImageView);

        mTabImageViews.add(mViberTabImageView);
        mTabImageViews.add(mWhatsappTabImageView);
        mTabImageViews.add(mTelegramTabImageView);
        mTabImageViews.add(mSmsTabImageView);

        int tabIdx = 0;

        for (ImageView imageView : mTabImageViews) {
            final Integer currentTabIdx = tabIdx;
            Log.d("test0", currentTabIdx.toString());
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("test1", "1");
                    for (LinearLayout linearLayout : mTabLayouts) {

                        Log.d("test2", linearLayout.toString());
                        linearLayout.setBackgroundColor(getResources()
                                .getColor(android.R.color.transparent));
                    }

                    mTabLayouts.get(currentTabIdx)
                            .setBackgroundColor(Color.parseColor("#CCCCCC"));
                    mSelectedProvider = mProvidersSet.get(currentTabIdx);
                }
            });

            tabIdx++;
        }

        mLinkListView = (ListView) findViewById(R.id.listView);
        ButterKnife.bind(this);

        /*mLinkListView.setOnItemLongClickListener((parent, view, position, id) -> {

            Toast.makeText(
                    view.getContext()
                    , ((TextView)view.findViewById(R.id.linkListItemTitle)).getText(), Toast.LENGTH_LONG);
            return true;
        });*/

        /*ImageView imageView1 = (ImageView) findViewById(R.id.viberTab);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("asd", "1");
                ButterKnife.apply(
                        mTabs
                        , (view, value, index) ->
                                view.setBackgroundColor(value)
                        , android.R.color.transparent
                );
                //Log.i("asd", view.getBackground().toString());
                //mTabs.get(0).setBackgroundColor(Color.parseColor("#CCCCCC"));
                ((LinearLayout) findViewById(R.id.viberTabLayout))
                        .setBackgroundColor(Color.parseColor("#CCCCCC"));
            }
        });*/

        /*ImageView imageView2 = (ImageView) findViewById(R.id.smsTab);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("asd", "4");
                ButterKnife.apply(
                        mTabs
                        , (view, value, index) ->
                                view.setBackgroundColor(value)
                        , android.R.color.transparent
                );
                //Log.i("asd", view.getBackground().toString());
                //mTabs.get(3).setBackgroundColor(Color.parseColor("#CCCCCC"));
                ((LinearLayout) findViewById(R.id.smsTabLayout))
                        .setBackgroundColor(Color.parseColor("#CCCCCC"));
            }
        });*/

        initProviders();

        registerForContextMenu(mLinkListView);
    }

    private final void bindData() {
        if (mActivityBusinessCardBinding != null) return;

        Global.init();

        mActivityBusinessCardBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_business_card);
        final LinkList linkList = Global.LINK_LIST;
        mActivityBusinessCardBinding.setItems(linkList);
    }

    private void initProviders(){
        messageHelper = MessageHelper.getInstance(this);

        smsProvider = new SMSProvider(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                String s;

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        s = getResources().getString(R.string.sMessageSentSuccessfully);
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        s = getResources().getString(R.string.sGenericFailure);
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        s = getResources().getString(R.string.sNoServiceAvailable);
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        s = getResources().getString(R.string.sNullPDU);
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        s = getResources().getString(R.string.sRadioIsOff);
                        break;
                    default:
                        s = getResources().getString(R.string.sUnknownError) + getResultCode() + ")";
                        break;
                }

                notifyAboutSendingResult(s);
            }});

        messageHelper.registerProvider(Provider.Sms.name(), smsProvider);
        //messageHelper.registerProvider(Provider.Telegram.name(), telegrProvider);
        //messageHelper.registerProvider(Provider.Whatsapp.name(), wtsappProvider);
        //messageHelper.registerProvider(Provider.Viber.name(), viberProvider);
    }

    private void notifyAboutSendingResult(String message) {
        //TODO code notyfiAboutSendingResult
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_group_edit, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.miShowGroup: {
                int index = //((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;
                        getIntent().getIntExtra(LinkListItemAdapter.MENU_ITEM_INDEX, -1);
                Intent intent = new Intent(this, LinksEditActivity.class);
                Long id = ((LinkListItem) mLinkListView.getAdapter().getItem(index)).getId();
                intent.putExtra(
                        BusinessCardActivity.GROUP_ID
                        , id
                );

                startActivityForResult(intent, BusinessCardActivity.EDIT_GROUP_REQUEST);
            }   return true;
            case R.id.miDeleteGroup:
                //if (Global.LINK_LIST.mLinkItemList.size() > 0){
                    int index = //((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
                            getIntent().getIntExtra(LinkListItemAdapter.MENU_ITEM_INDEX, -1);
                    getIntent().putExtra("index", index);

                    dlgYesNo = new Dialog2Buttons();
                    Bundle b = new Bundle();
                    b.putString("msg", getResources().getString(R.string.qDeleteGroup));

                    dlgYesNo.setArguments(b);

                    dlgYesNo.show(getFragmentManager(), "dlgyn");
              //  }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    //Обработчик клика для кнопок добавления прикреплений
    @OnClick({
            R.id.exitImageButton
            , R.id.addGroupImageButton
            , R.id.sendImageButton
            , R.id.fab})
    void onClick(View view) {
        //Log.i("asd", "Test text 0");
        switch (view.getId()) {
            case R.id.exitImageButton:
                //exit from app
                finish();
                break;
            case R.id.addGroupImageButton:
                Intent intent = new Intent(this, LinksEditActivity.class);
                startActivityForResult(intent, BusinessCardActivity.EDIT_GROUP_REQUEST);
                break;
            case R.id.sendImageButton:
                //
                switch (mSelectedProvider){
                    case Sms: {
                        if (mPhoneNumber.equals("")){
                            goToContactsActivity();
                        } else {
                            final ArrayList<LinkListItem> list = new ArrayList<>();
                            final ObservableArrayList<LinkListItem> li = Global.LINK_LIST.mLinkItemList;

                            for (int j = 0; j < li.size(); ++j)
                                if (li.get(j).checked)
                                   list.add(li.get(j));

                            final ProviderData data = messageHelper.getProviderData(Provider.Sms.name());
                            data.enabled = true;
                            data.provider.setTempOptions(new SMSProviderOptions(mPhoneNumber));
                            messageHelper.sendMessages(list);//Global.LINK_LIST.mLinkItemList);

                            mPhoneNumber = "";
                        }
                    }   break;
                    case Telegram:
                        break;
                    case Viber:
                        break;
                    case Whatsapp:
                        break;
                }
                break;
            case R.id.fab:
                goToContactsActivity();
                break;
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

    private void removeItem(int index){
        //Object it = mLinkListView.getAdapter().getItem(index);
        //Global.LINK_LIST.mLinkItemList.get(index)
        LinkListItem li = Global.LINK_LIST.mLinkItemList.remove(index);
        //TODO check it
        //apply changes in database
        Global.greenDAOFacade.deleteLinksGroupByKey(li.getId());
        //Global.greenDAOFacade.updateLinksGroup(linksGroup);
        //mActivityBusinessCardBinding.invalidateAll();
    }

    private void goToContactsActivity(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, CONTACTS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT_GROUP_REQUEST){
                /*int groupIndex = data.getIntExtra(EditGroupActivity.GROUP_ID, 0);
                String title = data.getStringExtra(EditGroupActivity.GROUP_NAME);

                //edit group
                if (groupIndex > -1) {//change
                    LinkListItem item = Global.LINK_LIST.mLinkItemList.get(groupIndex);
                    item.title = title;
                 //   item.drawable = ?
                }
                else//create group
                {//change
                    LinksGroup linksGroup =
                            Global.greenDAOFacade.createLinksGroup(
                                    title
                                    , true
                                    , Utility.drawableToURIString(this, R.drawable.vk)
                            );
                    Global.greenDAOFacade.updateLinksGroup(linksGroup);
                    Global.linksGroupList.add(linksGroup);
                 //   Long id = ???Global.;
                  //  Drawable drawable = getResources().getDrawable(R.drawable.defaultImageForGroup);
                 //   SubLinkList subLinks = new SubLinkList();

                  //  LinkListItem item = new LinkListItem(id, title, drawable, true, subLinks);
                 //   Global.LINK_LIST.mLinkItemList.add(item);
                }*/
                //
                mActivityBusinessCardBinding.invalidateAll();
            }
            else
            if (requestCode == CONTACTS_REQUEST){
                // Get the URI and query the content provider for the phone number
                Uri contactUri = data.getData();
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection,
                        null, null, null);

                // If the cursor returned is valid, get the phone number
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        mPhoneNumber = cursor.getString(numberIndex);
                        //Log.d("my", mPhoneNumber);
                        // Do something with the phone number
                        //mPhoneNumber = data.getStringExtra(ContactsActivity.EXTRA_PHONE_NUMBER);
                    }

                    cursor.close();
                }
            }
        }
    }

    private void checkPermission(){
        if (!Utility.isThereAPermission(this, android.Manifest.permission.READ_PHONE_STATE)
           || !Utility.isThereAPermission(this, android.Manifest.permission.READ_CONTACTS)
           || !Utility.isThereAPermission(this, android.Manifest.permission.SEND_SMS)
           || !Utility.isThereAPermission(this, Manifest.permission.CAMERA))
        {
            Intent checkPermissionIntent = new Intent(this, PermissionActivity.class);
            checkPermissionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(checkPermissionIntent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    bindData();
                }
                else {
                    //code for deny
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO инкапсулировать
        if (smsProvider != null)
            smsProvider.resume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        //TODO инкапсулировать
        if (smsProvider != null)
            smsProvider.reset(this);
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!mPhoneNumber.equals("")){
            outState.putString(STATE_PHONE_NUMBER, mPhoneNumber);
        } else {

            //outState = null;
        }

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }
}
