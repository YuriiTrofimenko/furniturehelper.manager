package org.tyaa.furniturehelper.manager.common;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.entity.LinkImgItem;
import org.tyaa.furniturehelper.manager.entity.LinkTextItem;
import org.tyaa.furniturehelper.manager.entity.LinkUrlItem;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yurii on 30.11.17.
 */

public class Generator {
    private static Context mContext;

    static {
        mContext = CurrentApplication.getAppContext();
    }

    /*public static ObservableArrayList<LinkListItem> getLinkListBasis(){

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
        //linkListItem.subLinks.mSubLinks.add(subLink);
        //linkListItem.subLinks.mSubLinks.add(subLink);
        //linkListItem.subLinks.mSubLinks.add(subLink);

        SubLinkText subLinkText = new SubLinkText();
        subLinkText.id = 0L;
        subLinkText.text = "test text 1";
        linkListItem.subLinks.mSubLinks.add(subLinkText);

        SubLinkLink subLinkLink = new SubLinkLink();
        subLinkLink.id = 1L;
        subLinkLink.link = "test link 1";
        linkListItem.subLinks.mSubLinks.add(subLinkLink);

        SubLinkImg subLinkImg = new SubLinkImg();
        subLinkImg.id = 2L;
        subLinkImg.drawable = Global.EMPTY_DRAWABLE;
        linkListItem.subLinks.mSubLinks.add(subLinkImg);

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
    }*/

    /**
     * Метод создания демонстрационных данных ссылок для БД
     * */
    public static List<LinksGroup> generateLinksGroups(){//TODO строки в ресурсы добавить
        Resources res = mContext.getResources();
        /**
         * Список для групп ссылок
         * */
        List<LinksGroup> linksGroups = new ArrayList<>();
        /**
         * Группа ссылок из ВК
         * */
        //Log.d("MyLog", Utility.drawableToURI(mContext, R.drawable.vk).toString());
        String uriString = Utility.drawableToURIString(mContext, R.drawable.vk);
        Log.d("MyLog", uriString);

        LinksGroup linksGroup =
                Global.greenDAOFacade.createLinksGroup("ВКонтакте", true, uriString);
        /**
         * Добавление текстового элемента в группу
         * */
        LinkTextItem linkTextItem = new LinkTextItem();
        linkTextItem.setText(res.getString(R.string.sFeedback));
        linkTextItem.setGuid(Generator.generateGuid());
        linkTextItem.setLinksGroup(linksGroup);
        Global.greenDAOFacade.createLink(linkTextItem);
        linksGroup.linkTextItems = new ArrayList<>();
        linksGroup.linkTextItems.add(linkTextItem);
        /**
         * Добавление элемента-ссылки в группу
         * */
        LinkUrlItem linkUrlItem = new LinkUrlItem();
        linkUrlItem.setLink("www.vk.com/topic-115792435_34460571");
        linkUrlItem.setLinksGroup(linksGroup);
        linkUrlItem.setGuid(Generator.generateGuid());
        Global.greenDAOFacade.createLink(linkUrlItem);
        linksGroup.linkUrlItems = new ArrayList<>();
        linksGroup.linkUrlItems.add(linkUrlItem);
        /**
         * Добавление графического элемента в группу
         * */
        LinkImgItem linkImgItem = new LinkImgItem();
        linkImgItem.setDrawable(Utility.drawableToURIString(mContext, R.drawable.lidzalogo));
        linkImgItem.setLinksGroup(linksGroup);
        linkImgItem.setGuid(Generator.generateGuid());
        Global.greenDAOFacade.createLink(linkImgItem);
        linksGroup.linkImgItems = new ArrayList<>();
        linksGroup.linkImgItems.add(linkImgItem);
        Log.d("MyLog", linksGroup.linkImgItems.get(0).getDrawable());
        /**
         * Добаление текстового элемента в группу
         * */
        linkTextItem = new LinkTextItem();
        linkTextItem.setText("Шкафчик мебельная торговая сеть в Мариуполе");
        linkTextItem.setLinksGroup(linksGroup);
        linkTextItem.setGuid(Generator.generateGuid());
        Global.greenDAOFacade.createLink(linkTextItem);
        //linksGroup.linkTextItems = new ArrayList<>();
        linksGroup.linkTextItems.add(linkTextItem);
        /**
         * Добаление элемента-ссылки в группу
         * */
        linkUrlItem = new LinkUrlItem();
        linkUrlItem.setLink("www.vk.com/lidzacom");
        linkUrlItem.setLinksGroup(linksGroup);
        linkUrlItem.setGuid(Generator.generateGuid());
        Global.greenDAOFacade.createLink(linkUrlItem);
        //linksGroup.linkUrlItems = new ArrayList<>();
        linksGroup.linkUrlItems.add(linkUrlItem);
        /**
         * Добаление текстового элемента в группу
         * */
        linkTextItem = new LinkTextItem();
        linkTextItem.setText("Александр Дорошеко");
        linkTextItem.setLinksGroup(linksGroup);
        linkTextItem.setGuid(Generator.generateGuid());
        Global.greenDAOFacade.createLink(linkTextItem);
        //linksGroup.linkTextItems = new ArrayList<>();
        linksGroup.linkTextItems.add(linkTextItem);
        /**
         * Добаление элемента-ссылки в группу
         * */
        linkUrlItem = new LinkUrlItem();
        linkUrlItem.setLink("https://vk.com/shkafchik30");
        linkUrlItem.setLinksGroup(linksGroup);
        linkUrlItem.setGuid(Generator.generateGuid());
        Global.greenDAOFacade.createLink(linkUrlItem);
        //linksGroup.linkUrlItems = new ArrayList<>();
        linksGroup.linkUrlItems.add(linkUrlItem);

        Global.greenDAOFacade.updateLinksGroup(linksGroup);

        /**
         * Добаление группы, заполненной элементами, в список
         * */
        linksGroups.add(linksGroup);

        /**
         * Группа ссылок из Фейсбук
         * */
        linksGroup =
                Global.greenDAOFacade.createLinksGroup(
                        "Facebook"
                        , true
                        , Utility.drawableToURIString(mContext, R.drawable.facebook).toString()
                );
        /**
         * Добаление текстового элемента в группу
         * */
        linkTextItem = new LinkTextItem();
        linkTextItem.setText("Шкафчик торговая сеть мебельных магазинов в Мариуполе");
        linkTextItem.setLinksGroup(linksGroup);
        linkTextItem.setGuid(Generator.generateGuid());
        Global.greenDAOFacade.createLink(linkTextItem);
        linksGroup.linkTextItems = new ArrayList<>();
        linksGroup.linkTextItems.add(linkTextItem);
        /**
         * Добаление элемента-ссылки в группу
         * */
        linkUrlItem = new LinkUrlItem();
        linkUrlItem.setLink("https://www.facebook.com/SHKAFCHIK30");
        linkUrlItem.setLinksGroup(linksGroup);
        linkUrlItem.setGuid(Generator.generateGuid());
        Global.greenDAOFacade.createLink(linkUrlItem);
        linksGroup.linkUrlItems = new ArrayList<>();
        linksGroup.linkUrlItems.add(linkUrlItem);

        Global.greenDAOFacade.updateLinksGroup(linksGroup);

        /**
         * Добаление группы, заполненной элементами, в список
         * */
        linksGroups.add(linksGroup);

        return linksGroups;
    }

    public static String generateGuid() {
        //return UUID.randomUUID().toString();
        return String.valueOf(new Date().getTime());
    }
}
