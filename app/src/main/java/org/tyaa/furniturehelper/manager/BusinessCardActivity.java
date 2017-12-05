package org.tyaa.furniturehelper.manager;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.tyaa.furniturehelper.manager.common.Global;
import org.tyaa.furniturehelper.manager.databinding.ActivityBusinessCardBinding;
import org.tyaa.furniturehelper.manager.model.LinkList;
import org.tyaa.furniturehelper.manager.model.LinkListItem;
import org.tyaa.furniturehelper.manager.receiver.CallReceiver;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTouch;

public class BusinessCardActivity extends AppCompatActivity {

    //@BindViews({R.id.viberTabLayout, R.id.whatsappTabLayout, R.id.telegramTabLayout, R.id.smsTabLayout})
    //List<View> mTabs;

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

    private String mPhoneNumber = "";

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

        setContentView(R.layout.activity_business_card);
        //ButterKnife.bind(this);

        mPhoneNumber =
                getIntent().getStringExtra(CallReceiver.EXTRA_PHONE_NUMBER);
        Toast.makeText(this, mPhoneNumber,
                Toast.LENGTH_SHORT).show();

        ActivityBusinessCardBinding activityBusinessCardBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_business_card);
        final LinkList linkList = Global.LINK_LIST;
        activityBusinessCardBinding.setItems(linkList);

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
                }
            });
            tabIdx++;
        }

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
}
