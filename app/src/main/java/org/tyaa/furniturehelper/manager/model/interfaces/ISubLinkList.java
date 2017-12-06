package org.tyaa.furniturehelper.manager.model.interfaces;

import android.databinding.ObservableArrayList;
import android.view.View;

import org.tyaa.furniturehelper.manager.model.LinkListItem;
import org.tyaa.furniturehelper.manager.model.SubLink;

/**
 * Created by yurii on 30.11.17.
 */

public interface ISubLinkList {

    void setSub_link(SubLink subLink);
    void setSub_link_list(ObservableArrayList<SubLink> subLinks);
    void add(View view);
    void remove(View view);
    Integer getSelectedItemPosition();
    void setSelectedItemPosition(Integer selectedItemPosition);
}
