package org.tyaa.furniturehelper.manager.common;

import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.adapter.EntitiesModelsAdapter;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.entity.interfaces.ILinkItem;
import org.tyaa.furniturehelper.manager.model.LinkList;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

import java.util.List;

/**
 * Created by yurii on 30.11.17.
 */

public class Global {

    public static final LinkList LINK_LIST;
    public static ImageView selectedImageView;
    public static final Drawable EMPTY_DRAWABLE;
    public static int selectedSubLinkPos;
    public static GreenDAOFacade greenDAOFacade;
    public static List<LinksGroup> linksGroupList;

    static {

        //greenDAOFacade = new GreenDAOFacade();
        initRepository();
        EMPTY_DRAWABLE =
                CurrentApplication.getAppContext()
                        .getResources()
                        .getDrawable(R.drawable.empty);
        LINK_LIST = new LinkList();
        //LINK_LIST.setLink_list(Generator.getLinkListBasis());
        LINK_LIST.setLink_list(EntitiesModelsAdapter.populateLinkList(linksGroupList));
    }

    /**
     * Первичная настройка репозитория
     * */
    private static void initRepository(){

        //Пытаемся получить список всех групп ссылок из БД
        linksGroupList = greenDAOFacade.getAllLinksGroups();
        //Если ничего не получено - значит БД еще не создавалась
        if (linksGroupList == null){

            //Создаем список групп, заполненных элементами
            Generator.generateLinksGroups();

            /*for (LinksGroup linksGroup : Generator.generateLinksGroups()) {

                //Делаем в БД запись о каждой группе
                greenDAOFacade.createLinksGroup(
                        linksGroup.getTitle()
                        , linksGroup.getChecked()
                        , linksGroup.getDrawable()
                );
                for (ILinkItem linkItem : linksGroup.links) {

                }
            }*/
            linksGroupList = greenDAOFacade.getAllLinksGroups();
        }
    }
}
