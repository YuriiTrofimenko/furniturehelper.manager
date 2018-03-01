package org.tyaa.furniturehelper.manager.adapter;

import android.databinding.ObservableArrayList;
import android.util.Log;

import org.tyaa.fhelpermodel.LinkListItem;
import org.tyaa.fhelpermodel.SubLinkImg;
import org.tyaa.fhelpermodel.SubLinkLink;
import org.tyaa.fhelpermodel.SubLinkList;
import org.tyaa.fhelpermodel.SubLinkMap;
import org.tyaa.fhelpermodel.SubLinkText;
import org.tyaa.fhelpermodel.interfaces.ISubLink;
import org.tyaa.furniturehelper.manager.common.Utility;
import org.tyaa.furniturehelper.manager.entity.LinkImgItem;
import org.tyaa.furniturehelper.manager.entity.LinkMapItem;
import org.tyaa.furniturehelper.manager.entity.LinkTextItem;
import org.tyaa.furniturehelper.manager.entity.LinkUrlItem;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.entity.interfaces.ILinkItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yurii on 26.12.17.
 */

public class EntitiesModelsAdapter {
    public static ObservableArrayList<LinkListItem> populateLinkList(List<LinksGroup> _linksGroups){
        ObservableArrayList<LinkListItem> linkListItems =
                new ObservableArrayList<>();

        for (LinksGroup linksGroup : _linksGroups) {
            LinkListItem linkListItem =
                    new LinkListItem(
                            linksGroup.getId()
                            , linksGroup.getTitle()
                            , Utility.uriStringToDrawable(linksGroup.getDrawable())
                            , linksGroup.getChecked()
                            , new SubLinkList()
                    );
            if (linksGroup.linkTextItems != null) {
                for (LinkTextItem linkTextItem : linksGroup.linkTextItems) {
                    SubLinkText subLinkText = new SubLinkText();
                    subLinkText.id = linkTextItem.getId();
                    subLinkText.text = linkTextItem.getText();
                    subLinkText.guid = linkTextItem.getGuid();
                    linkListItem.subLinks.mSubLinks.add(subLinkText);
                }
            }

            if (linksGroup.linkUrlItems != null) {
                for (LinkUrlItem linkUrlItem : linksGroup.linkUrlItems) {
                    SubLinkLink subLinkLink = new SubLinkLink();
                    subLinkLink.id = linkUrlItem.getId();
                    subLinkLink.link = linkUrlItem.getLink();
                    subLinkLink.guid = linkUrlItem.getGuid();
                    linkListItem.subLinks.mSubLinks.add(subLinkLink);
                }
            }

            if (linksGroup.linkMapItems != null) {
                for (LinkMapItem linkMapItem : linksGroup.linkMapItems) {
                    SubLinkMap subLinkMap = new SubLinkMap();
                    subLinkMap.id = linkMapItem.getId();
                    subLinkMap.map = linkMapItem.getLink();
                    subLinkMap.guid = linkMapItem.getGuid();
                    linkListItem.subLinks.mSubLinks.add(subLinkMap);
                }
            }

            if (linksGroup.linkImgItems != null) {
                for (LinkImgItem linkImgItem : linksGroup.linkImgItems) {
                    SubLinkImg subLinkImg = new SubLinkImg();
                    subLinkImg.id = linkImgItem.getId();
                    subLinkImg.drawable = Utility.uriStringToDrawable(linkImgItem.getDrawable());
                    subLinkImg.guid = linkImgItem.getGuid();
                    linkListItem.subLinks.mSubLinks.add(subLinkImg);
                }
            }

            Collections.sort(linkListItem.subLinks.mSubLinks, new Comparator<ISubLink>() {
                @Override
                public int compare(ISubLink o1, ISubLink o2) {
                    //return o1.getId().intValue() - o2.getId().intValue();
                    return o1.getGuid().compareTo(o2.getGuid());
                }
            });

            linkListItems.add(linkListItem);
        }

        for (ISubLink subLink : linkListItems.get(0).subLinks.mSubLinks) {
            Log.d("asd", subLink.getGuid());
        }

        return linkListItems;
    }

    public static ISubLink linkItemToSubLink(ILinkItem _linkItem){
        ISubLink subLink = null;

        if (_linkItem instanceof LinkTextItem) {
            LinkTextItem linkTextItem = (LinkTextItem) _linkItem;
            SubLinkText subLinkText = new SubLinkText();
            subLinkText.id = linkTextItem.getId();
            subLinkText.text = linkTextItem.getText();
            subLinkText.guid = linkTextItem.getGuid();
            subLink = subLinkText;
        }
        else if (_linkItem instanceof LinkUrlItem) {
            LinkUrlItem linkUrlItem = (LinkUrlItem) _linkItem;
            SubLinkLink subLinkLink = new SubLinkLink();
            subLinkLink.id = linkUrlItem.getId();
            subLinkLink.link = linkUrlItem.getLink();
            subLinkLink.guid = linkUrlItem.getGuid();
            subLink = subLinkLink;
        }
        else if (_linkItem instanceof LinkMapItem) {
            LinkMapItem linkMapItem = (LinkMapItem) _linkItem;
            SubLinkMap subLinkMap = new SubLinkMap();
            subLinkMap.id = linkMapItem.getId();
            subLinkMap.map = linkMapItem.getLink();
            subLinkMap.guid = linkMapItem.getGuid();
            subLink = subLinkMap;
        }
        else /*if (_linkItem instanceof LinkImgItem)*/ {
            LinkImgItem linkImgItem = (LinkImgItem) _linkItem;
            SubLinkImg subLinkImg = new SubLinkImg();
            subLinkImg.id = linkImgItem.getId();
            subLinkImg.drawable = Utility.uriStringToDrawable(linkImgItem.getDrawable());
            subLinkImg.guid = linkImgItem.getGuid();
            subLink = subLinkImg;
        }

        return subLink;
    }
}
