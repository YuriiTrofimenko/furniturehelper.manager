package org.tyaa.furniturehelper.manager.adapter;

import android.databinding.ObservableArrayList;

import org.tyaa.furniturehelper.manager.common.Utility;
import org.tyaa.furniturehelper.manager.entity.LinkImgItem;
import org.tyaa.furniturehelper.manager.entity.LinkMapItem;
import org.tyaa.furniturehelper.manager.entity.LinkTextItem;
import org.tyaa.furniturehelper.manager.entity.LinkUrlItem;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.entity.interfaces.ILinkItem;
import org.tyaa.furniturehelper.manager.model.LinkListItem;
import org.tyaa.furniturehelper.manager.model.SubLinkImg;
import org.tyaa.furniturehelper.manager.model.SubLinkLink;
import org.tyaa.furniturehelper.manager.model.SubLinkList;
import org.tyaa.furniturehelper.manager.model.SubLinkMap;
import org.tyaa.furniturehelper.manager.model.SubLinkText;
import org.tyaa.furniturehelper.manager.model.interfaces.ISubLink;

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
                    linkListItem.subLinks.mSubLinks.add(subLinkText);
                }
            }

            if (linksGroup.linkUrlItems != null) {

                for (LinkUrlItem linkUrlItem : linksGroup.linkUrlItems) {

                    SubLinkLink subLinkLink = new SubLinkLink();
                    subLinkLink.id = linkUrlItem.getId();
                    subLinkLink.link = linkUrlItem.getLink();
                    linkListItem.subLinks.mSubLinks.add(subLinkLink);
                }
            }

            if (linksGroup.linkMapItems != null) {

                for (LinkMapItem linkMapItem : linksGroup.linkMapItems) {

                    SubLinkMap subLinkMap = new SubLinkMap();
                    subLinkMap.id = linkMapItem.getId();
                    subLinkMap.map = linkMapItem.getLink();
                    linkListItem.subLinks.mSubLinks.add(subLinkMap);
                }
            }

            if (linksGroup.linkImgItems != null) {

                for (LinkImgItem linkImgItem : linksGroup.linkImgItems) {

                    SubLinkImg subLinkImg = new SubLinkImg();
                    subLinkImg.id = linkImgItem.getId();
                    subLinkImg.drawable = Utility.uriStringToDrawable(linkImgItem.getDrawable());
                    linkListItem.subLinks.mSubLinks.add(subLinkImg);
                }
            }

            Collections.sort(linkListItem.subLinks.mSubLinks, new Comparator<ISubLink>() {
                @Override
                public int compare(ISubLink o1, ISubLink o2) {
                    return o1.getId().intValue() - o2.getId().intValue();
                }
            });

            linkListItems.add(linkListItem);
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
            subLink = subLinkText;
        } else if (_linkItem instanceof LinkUrlItem) {

        } else if (_linkItem instanceof LinkMapItem) {

        } else /*if (_linkItem instanceof LinkImgItem)*/ {

        }

        return subLink;
    }
}
