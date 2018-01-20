package org.tyaa.furniturehelper.manager;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.tyaa.fhelpermodel.LinkList;
import org.tyaa.furniturehelper.manager.common.Global;
import org.tyaa.furniturehelper.manager.databinding.ActivityBusinessCardBinding;
import org.tyaa.furniturehelper.manager.receiver.CallReceiver;
import org.tyaa.furnituresender.messagehelper.MessageHelper;
import org.tyaa.furnituresender.messageproviders.SMSProvider;
import org.tyaa.furnituresender.messageproviders.SMSProviderOptions;
import org.tyaa.furnituresender.messageproviders.base.MessageProviderOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static org.tyaa.furnituresender.messageproviders.base.MessageProviderOptions.getContext;

public class BusinessCardActivity extends AppCompatActivity {

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

    private List<LinearLayout> mTabLayouts = new ArrayList<>();

    private ImageView mViberTabImageView;
    private ImageView mWhatsappTabImageView;
    private ImageView mTelegramTabImageView;
    private ImageView mSmsTabImageView;

    private List<ImageView> mTabImageViews = new ArrayList<>();

    private ListView mLinkListView;

    private String mPhoneNumber = "";

    private enum Provider {

        Viber
        , Whatsapp
        , Telegram
        , Sms
    }

    private Map<Integer, Provider> mProvidersSet = new HashMap<>();

    private Provider mSelectedProvider = Provider.Viber;

    private ActivityBusinessCardBinding mActivityBusinessCardBinding;

    public static final String SELECTED_LINK_LIST_ITEM_TITLE =
            "org.tyaa.furniturehelper.manager.AppCompatActivity.SELECTED_TITLE";

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

        mProvidersSet.put(0, Provider.Viber);
        mProvidersSet.put(1, Provider.Whatsapp);
        mProvidersSet.put(2, Provider.Telegram);
        mProvidersSet.put(3, Provider.Sms);

        setContentView(R.layout.activity_business_card);

        Intent incomingIntent = getIntent();

        if (incomingIntent.hasExtra(CallReceiver.EXTRA_PHONE_NUMBER)){

            mPhoneNumber =
                    getIntent().getStringExtra(CallReceiver.EXTRA_PHONE_NUMBER);
            Toast.makeText(this, mPhoneNumber,
                    Toast.LENGTH_SHORT).show();
        } else {

            Toast.makeText(this, "TODO",
                    Toast.LENGTH_SHORT).show();
        }

        mActivityBusinessCardBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_business_card);
        final LinkList linkList = Global.LINK_LIST;
        mActivityBusinessCardBinding.setItems(linkList);
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

            final int currentTabIdx = tabIdx;
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    for (LinearLayout linearLayout : mTabLayouts) {

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
    }

    //Обработчик клика для кнопок добавления прикреплений
    @OnClick({
            R.id.exitImageButton
            , R.id.addGroupImageButton
            , R.id.sendImageButton
            , R.id.fab
    })
    void onClick(View view) {
        //Log.i("asd", "Test text 0");
        switch (view.getId()) {

            case R.id.exitImageButton:
                //
                break;
            case R.id.addGroupImageButton:
                //

                break;
            case R.id.sendImageButton:
                //
                /*Toast.makeText(
                        this
                        , "Test text", Toast.LENGTH_LONG).show();
                Log.i("asd", "Test text");*/
                switch (mSelectedProvider){

                    case Sms: {

                        if (mPhoneNumber.equals("")){

                            goToContactsActivity();
                        } else {

                            MessageHelper messageHelper = MessageHelper.getInstance(this);
                            SMSProvider smsProvider = new SMSProvider();
                            smsProvider.setTempOptions(new SMSProviderOptions(mPhoneNumber));
                            messageHelper.registerProvider(mSelectedProvider.name(), smsProvider);
                            messageHelper.sendMessages(Global.LINK_LIST.mLinkItemList);
                        }
                        break;
                    }
                }
                break;
            case R.id.fab:

                goToContactsActivity();
                break;
        }
    }

    private void goToContactsActivity(){

        //
        //Intent intent = new Intent(this, ContactsActivity.class);
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, CONTACTS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == EDIT_GROUP_REQUEST){

                //
                mActivityBusinessCardBinding.invalidateAll();
            }
            else if (requestCode == CONTACTS_REQUEST){

                // Get the URI and query the content provider for the phone number
                Uri contactUri = data.getData();
                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(contactUri, projection,
                        null, null, null);

                // If the cursor returned is valid, get the phone number
                if (cursor != null && cursor.moveToFirst()) {

                    int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    mPhoneNumber = cursor.getString(numberIndex);
                    Log.d("my", mPhoneNumber);
                    // Do something with the phone number
                    //mPhoneNumber = data.getStringExtra(ContactsActivity.EXTRA_PHONE_NUMBER);
                }

                cursor.close();
            }
        }
    }
}
