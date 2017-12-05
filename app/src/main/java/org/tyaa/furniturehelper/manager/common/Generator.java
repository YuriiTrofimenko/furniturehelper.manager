package org.tyaa.furniturehelper.manager.common;

import android.content.Context;
import android.databinding.ObservableArrayList;

import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.model.Link;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

import java.util.ArrayList;

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
                        , context.getResources().getDrawable(R.drawable.vk)
                        , true
                        , new ArrayList<Link>()
                );
        linkListItem.links.add(new Link());
        linkListItem.links.add(new Link());
        linkListItem.links.add(new Link());
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Facebook"
                        , context.getResources().getDrawable(R.drawable.facebook)
                        , true
                        , new ArrayList<Link>()
                );
        linkListItem.links.add(new Link());
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Instagram"
                        , context.getResources().getDrawable(R.drawable.instagram)
                        , true
                        , new ArrayList<Link>()
                );
        linkListItem.links.add(new Link());
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "YouTube"
                        , context.getResources().getDrawable(R.drawable.youtube)
                        , true
                        , new ArrayList<Link>()
                );
        linkListItem.links.add(new Link());
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Одноклассники"
                        , context.getResources().getDrawable(R.drawable.odnoklassniki)
                        , true
                        , new ArrayList<Link>()
                );
        linkListItem.links.add(new Link());
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Site"
                        , context.getResources().getDrawable(R.drawable.lidzalogo)
                        , true
                        , new ArrayList<Link>()
                );
        linkListItem.links.add(new Link());
        linkListItems.add(linkListItem);
        linkListItem =
                new LinkListItem(
                        "Google maps"
                        , context.getResources().getDrawable(R.drawable.google_maps)
                        , true
                        , new ArrayList<Link>()
                );
        linkListItem.links.add(new Link());
        linkListItem.links.add(new Link());
        linkListItem.links.add(new Link());
        linkListItems.add(linkListItem);
        return linkListItems;
    }
}