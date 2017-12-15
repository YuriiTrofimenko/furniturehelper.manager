package org.tyaa.furniturehelper.manager.common;

import android.content.Context;
import android.databinding.ObservableArrayList;

import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.model.SubLink;
import org.tyaa.furniturehelper.manager.model.LinkListItem;
import org.tyaa.furniturehelper.manager.model.SubLinkList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii on 30.11.17.
 */

public class Generator {

    public static ObservableArrayList<LinkListItem> getLinkListBasis(){

        Context context = CurrentApplication.getAppContext();

        ObservableArrayList<LinkListItem> linkListItems =
                new ObservableArrayList<>();

        SubLink subLink = new SubLink();
        subLink.text = "test text";
        subLink.link = "test link";
        subLink.drawable = Global.EMPTY_DRAWABLE;

        LinkListItem linkListItem =
                new LinkListItem(
                        "ВКонтакте"
                        , context.getResources().getDrawable(R.drawable.vk)
                        , true
                        , new SubLinkList()
                );
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Facebook"
                        , context.getResources().getDrawable(R.drawable.facebook)
                        , true
                        , new SubLinkList()
                );
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Instagram"
                        , context.getResources().getDrawable(R.drawable.instagram)
                        , true
                        , new SubLinkList()
                );
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "YouTube"
                        , context.getResources().getDrawable(R.drawable.youtube)
                        , true
                        , new SubLinkList()
                );
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Одноклассники"
                        , context.getResources().getDrawable(R.drawable.odnoklassniki)
                        , true
                        , new SubLinkList()
                );
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Site"
                        , context.getResources().getDrawable(R.drawable.lidzalogo)
                        , true
                        , new SubLinkList()
                );
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Google maps"
                        , context.getResources().getDrawable(R.drawable.google_maps)
                        , true
                        , new SubLinkList()
                );
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItem.subLinks.mSubLinks.add(subLink);
        linkListItems.add(linkListItem);
        return linkListItems;
    }

    public static List<LinksGroup> generateLinksGroups(){

        return null;
    }
}
