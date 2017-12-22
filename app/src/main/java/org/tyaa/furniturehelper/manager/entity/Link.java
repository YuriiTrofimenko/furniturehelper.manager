package org.tyaa.furniturehelper.manager.entity;

/**
 * Created by yurii on 15.12.17.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import java.util.List;

/**
 * One link model: description, hyperlink, picture
 */

@Entity(
        active = true,
        nameInDb = "Link"
)
public class Link {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "text")
    @NotNull
    private String text;

    //Коллекция прикрепленных обычных ссылок
    @ToMany(referencedJoinProperty = "linkId")
    private List<LinkUrlItem> linkUrlItems;

    //Коллекция прикрепленных ссылок на карты
    @ToMany(referencedJoinProperty = "linkId")
    private List<LinkMapItem> linkMapItems;

    //Коллекция прикрепленных изображений
    @ToMany(referencedJoinProperty = "linkId")
    private List<LinkImgItem> linkImgItems;

    //ИД группы, к которой пренадлежит данная "Ссылка"
    private Long linksGroupId;

    @ToOne(joinProperty = "linksGroupId")
    private LinksGroup linksGroup;
}
