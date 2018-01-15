package org.tyaa.fhelpermodel.interfaces;

import android.databinding.ObservableArrayList;
import android.view.View;

import org.tyaa.fhelpermodel.LinkListItem;

/**
 * Created by yurii on 30.11.17.
 */

public interface ILinkList {

    void setLink_list_item(LinkListItem link_list_item);
    void setLink_list(ObservableArrayList<LinkListItem> link_list);
    void add(View view);
    void remove(View view);
    Integer getSelectedItemPosition();
    void setSelectedItemPosition(Integer selectedItemPosition);
}
