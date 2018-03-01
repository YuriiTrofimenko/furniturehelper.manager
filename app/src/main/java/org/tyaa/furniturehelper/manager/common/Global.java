package org.tyaa.furniturehelper.manager.common;

import android.graphics.drawable.Drawable;
import android.util.Log;
//import android.widget.ImageButton;
import android.widget.ImageView;

import org.tyaa.fhelpermodel.LinkList;
import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.adapter.EntitiesModelsAdapter;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import java.util.List;

/**
 * Created by yurii on 30.11.17.
 */

public class Global {
    public static LinkList LINK_LIST;
    public static ImageView selectedImageView;
    //
    //public static ImageButton currentGroupImageButton;
    public static Drawable EMPTY_DRAWABLE;
    public static int selectedSubLinkPos;
    public static GreenDAOFacade greenDAOFacade;
    public static List<LinksGroup> linksGroupList;
    public static String currentUrl;

    static public void init(){
        if (greenDAOFacade != null) return;

        greenDAOFacade = new GreenDAOFacade();
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
        //greenDAOFacade.clearLinksGroups();
        //Пытаемся получить список всех групп ссылок из БД
        linksGroupList = greenDAOFacade.getAllLinksGroups();
        //Если ничего не получено - значит БД еще не создавалась
        Log.i("MyLog", "get groups 1");

        if (linksGroupList == null || linksGroupList.size() == 0){//при добавлении новых групп, учебные не исчезают

            Log.d("MyLog", "get groups 2");
            //Создаем список групп, заполненных элементами
            linksGroupList = Generator.generateLinksGroups();

            //linksGroupList = greenDAOFacade.getAllLinksGroups();
        }
    }
}
