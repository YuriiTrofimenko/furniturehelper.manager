package org.tyaa.furniturehelper.manager.adapter;

import android.databinding.ObservableArrayList;

import org.tyaa.furniturehelper.manager.common.Utility;
import org.tyaa.furniturehelper.manager.entity.LinkImgItem;
import org.tyaa.furniturehelper.manager.entity.LinkMapItem;
import org.tyaa.furniturehelper.manager.entity.LinkTextItem;
import org.tyaa.furniturehelper.manager.entity.LinkUrlItem;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.model.LinkListItem;
import org.tyaa.furniturehelper.manager.model.SubLinkImg;
import org.tyaa.furniturehelper.manager.model.SubLinkLink;
import org.tyaa.furniturehelper.manager.model.SubLinkList;
import org.tyaa.furniturehelper.manager.model.SubLinkMap;
import org.tyaa.furniturehelper.manager.model.SubLinkText;

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
                            linksGroup.getTitle()
                            , Utility.stringToDrawable(linksGroup.getDrawable())
                            , linksGroup.getChecked()
                            , new SubLinkList()
                    );
            for (LinkTextItem linkTextItem : linksGroup.linkTextItems) {

                SubLinkText subLinkText = new SubLinkText();
                subLinkText.id = linkTextItem.getId();
                subLinkText.text = linkTextItem.getText();
                linkListItem.subLinks.mSubLinks.add(subLinkText);
            }
            for (LinkUrlItem linkUrlItem : linksGroup.linkUrlItems) {

                SubLinkLink subLinkLink = new SubLinkLink();
                subLinkLink.id = linkUrlItem.getId();
                subLinkLink.link = linkUrlItem.getLink();
                linkListItem.subLinks.mSubLinks.add(subLinkLink);
            }
            for (LinkMapItem linkMapItem : linksGroup.linkMapItems) {

                SubLinkMap subLinkMap = new SubLinkMap();
                subLinkMap.id = linkMapItem.getId();
                subLinkMap.map = linkMapItem.getLink();
                linkListItem.subLinks.mSubLinks.add(subLinkMap);
            }
            for (LinkImgItem linkImgItem : linksGroup.linkImgItems) {

                SubLinkImg subLinkImg = new SubLinkImg();
                subLinkImg.id = linkImgItem.getId();
                subLinkImg.drawable = Utility.stringToDrawable(linkImgItem.getDrawable());
                linkListItem.subLinks.mSubLinks.add(subLinkImg);
            }
            linkListItems.add(linkListItem);
        }

        return linkListItems;
    }
}
