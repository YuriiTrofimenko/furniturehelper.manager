package org.tyaa.furniturehelper.manager;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
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

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTouch;

public class BusinessCardActivity extends AppCompatActivity {

    @BindViews({R.id.viberTab, R.id.whatsappTab, R.id.telegramTab, R.id.smsTab})
    List<View> mTabsList;

    private String mPhoneNumber = "";

    /*@OnClick({R.id.viberTab, R.id.whatsappTab, R.id.telegramTab, R.id.smsTab})
    void onClickTab(View linearLayout) {

        ButterKnife.apply(
                mTabsList
                , (view, value, index) ->
                        linearLayout.setBackgroundColor(value)
                , android.R.color.transparent
        );
        Log.i("asd", linearLayout.getBackground().toString());
        linearLayout.setBackgroundColor(Color.parseColor("#CCCCCC"));
        switch (linearLayout.getId()) {
            case R.id.viberTab:
                // ...
                break;
            case R.id.whatsappTab:
                // ...
                break;
            case R.id.telegramTab:
                // ...
                break;
            case R.id.smsTab:
                // ...
                break;
        }
        //return true;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_card);
        ButterKnife.bind(this);

        mPhoneNumber =
                getIntent().getStringExtra(CallReceiver.EXTRA_PHONE_NUMBER);
        Toast.makeText(this, mPhoneNumber,
                Toast.LENGTH_SHORT).show();

        ActivityBusinessCardBinding activityBusinessCardBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_business_card);
        final LinkList linkList = Global.LINK_LIST;
        activityBusinessCardBinding.setItems(linkList);
        /*Toast.makeText(this, String.valueOf(c.size()),
                Toast.LENGTH_SHORT).show();*/
        /*for (LinkListItem linkListItem:linkList.mLinkItemList) {
            Log.i("asd", linkListItem.title);
        }*/
        /*for (View view : mTabsList) {

            //Log.i("asd", "dsfdsgsg");
            view.setOnClickListener(v -> {
                Log.i("asd", "!!!");
                ButterKnife.apply(
                        mTabsList
                        , (view1, value, index) ->
                                view1.setBackgroundColor(value)
                        , android.R.color.transparent
                );
                Log.i("asd", view.getBackground().toString());
                view.setBackgroundColor(Color.parseColor("#CCCCCC"));
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("asd", "dsfdsgsg");
                }
            });
        }*/

        ImageView imageView = (ImageView) findViewById(R.id.smsTab);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("asd", "dsfdsgsg");
                ButterKnife.apply(
                        mTabsList
                        , (view, value, index) ->
                                view.setBackgroundColor(value)
                        , android.R.color.transparent
                );
                //Log.i("asd", view.getBackground().toString());
                v.setBackgroundColor(Color.parseColor("#CCCCCC"));
            }
        });
    }
}
