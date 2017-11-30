package org.tyaa.furniturehelper.manager.model;

import android.databinding.ObservableArrayList;
import android.view.View;

import org.tyaa.furniturehelper.manager.model.interfaces.ILinkList;

/**
 * Created by yurii on 30.11.17.
 */

public class LinkList implements ILinkList {

    public ObservableArrayList<LinkListItem> mLinkItemList;
    public LinkListItem mLinkListItem;
    public Integer mSelectedItemPosition = 0;

    public LinkList() {

        mLinkItemList =
                new ObservableArrayList<>();
    }

    @Override
    public void setLink_list_item(LinkListItem link_list_item) {

        mLinkItemList.add(link_list_item);
    }

    @Override
    public void setLink_list(ObservableArrayList<LinkListItem> link_list) {

        mLinkItemList = link_list;
    }

    @Override
    public void add(View view) {

        mLinkItemList.add(new LinkListItem());
    }

    @Override
    public void remove(View view) {

        if (!mLinkItemList.isEmpty()) {

            mLinkItemList.remove(0);
        }
    }

    @Override
    public Integer getSelectedItemPosition() {

        return mSelectedItemPosition;
    }

    @Override
    public void setSelectedItemPosition(Integer selectedItemPosition) {

        mSelectedItemPosition = selectedItemPosition;
        mLinkListItem = mLinkItemList.get(mSelectedItemPosition);
    }
}
