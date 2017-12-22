package org.tyaa.furniturehelper.manager.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.tyaa.furniturehelper.manager.entity.interfaces.ILinkItem;

/**
 * Attachment: link
 * Created by yurii on 21.12.17.
 */

@Entity(
        active = true,
        nameInDb = "LinkMapItem"
)
public class LinkMapItem implements ILinkItem {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "link")
    @NotNull
    private String link;

    //ИД "Ссылки", к которой пренадлежит данное прикрепление
    private Long linkId;

    @ToOne(joinProperty = "linkId")
    private Link parentLink;
}
