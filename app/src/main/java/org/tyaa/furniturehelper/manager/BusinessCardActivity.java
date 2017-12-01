package org.tyaa.furniturehelper.manager;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.tyaa.furniturehelper.manager.common.Global;
import org.tyaa.furniturehelper.manager.databinding.ActivityBusinessCardBinding;
import org.tyaa.furniturehelper.manager.model.LinkList;
import org.tyaa.furniturehelper.manager.model.LinkListItem;
import org.tyaa.furniturehelper.manager.receiver.CallReceiver;

public class BusinessCardActivity extends AppCompatActivity {

    private String mPhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_card);

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
        for (LinkListItem linkListItem:linkList.mLinkItemList) {
            Log.i("asd", linkListItem.title);
        }
    }
}
