package org.tyaa.furniturehelper.manager.model;

import android.databinding.ObservableArrayList;
import android.view.View;

import org.tyaa.furniturehelper.manager.model.interfaces.ILinkList;
import org.tyaa.furniturehelper.manager.model.interfaces.ISubLink;
import org.tyaa.furniturehelper.manager.model.interfaces.ISubLinkList;

/**
 * Created by yurii on 30.11.17.
 */

public class SubLinkList implements ISubLinkList {

    public LinkListItem mLinkListItem;
    public ObservableArrayList<ISubLink> mSubLinks;
    public ISubLink mSubLink;
    public Integer mSelectedItemPosition = 0;

    public SubLinkList() {

        mSubLinks =
                new ObservableArrayList<>();
    }

    public void setLink_list_item(LinkListItem linkListItem){

        mLinkListItem = linkListItem;
    }

    @Override
    public void setSub_link(ISubLink subLink) {

        mSubLinks.add(subLink);
    }

    @Override
    public void setSub_link_list(ObservableArrayList<ISubLink> subLinks) {

        mSubLinks = subLinks;
    }

    @Override
    public void add(View view) {

        mSubLinks.add(new ISubLink() {});
    }

    @Override
    public void remove(View view) {

        if (!mSubLinks.isEmpty()) {

            mSubLinks.remove(0);
        }
    }

    @Override
    public Integer getSelectedItemPosition() {

        return mSelectedItemPosition;
    }

    @Override
    public void setSelectedItemPosition(Integer selectedItemPosition) {

        mSelectedItemPosition = selectedItemPosition;
        mSubLink = mSubLinks.get(mSelectedItemPosition);
    }
}
