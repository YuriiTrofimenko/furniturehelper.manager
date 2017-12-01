package org.tyaa.furniturehelper.manager.common;

import android.content.Context;
import android.databinding.ObservableArrayList;

import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

/**
 * Created by yurii on 30.11.17.
 */

public class Generator {

    public static ObservableArrayList<LinkListItem> getLinkListBasis(){

        Context context = CurrentApplication.getAppContext();
        ObservableArrayList<LinkListItem> linkListItems =
                new ObservableArrayList<>();
        LinkListItem linkListItem =
                new LinkListItem(
                        "ВКонтакте"
                        , context.getResources().getDrawable(R.drawable.telegram)
                        , true
                        , null
                );
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Facebook"
                        , context.getResources().getDrawable(R.drawable.viber)
                        , true
                        , null
                );
        linkListItems.add(linkListItem);
        return linkListItems;
    }
}
