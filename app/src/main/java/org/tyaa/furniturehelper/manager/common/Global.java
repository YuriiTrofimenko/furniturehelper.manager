package org.tyaa.furniturehelper.manager.common;

import android.databinding.ObservableArrayList;

import org.tyaa.furniturehelper.manager.model.LinkList;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

/**
 * Created by yurii on 30.11.17.
 */

public class Global {

    public static final LinkList LINK_LIST;

    static {

        LINK_LIST = new LinkList();
        LINK_LIST.setLink_list(Generator.getLinkListBasis());
    }
}
