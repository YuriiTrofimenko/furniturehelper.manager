package org.tyaa.furniturehelper.manager;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.tyaa.furniturehelper.manager.common.Global;
import org.tyaa.furniturehelper.manager.databinding.ActivityLinksEditBinding;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

public class LinksEditActivity extends AppCompatActivity {

    private int mLinksId;
    private LinkListItem mLinkListItem;

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
}
