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

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 309311183)
private transient LinkDao myDao;

@Generated(hash = 1030777686)
public Link(Long id, @NotNull String text, Long linksGroupId) {
    this.id = id;
    this.text = text;
    this.linksGroupId = linksGroupId;
}

@Generated(hash = 225969300)
public Link() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getText() {
    return this.text;
}

public void setText(String text) {
    this.text = text;
}

public Long getLinksGroupId() {
    return this.linksGroupId;
}

public void setLinksGroupId(Long linksGroupId) {
    this.linksGroupId = linksGroupId;
}

@Generated(hash = 1566396435)
private transient Long linksGroup__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 34970653)
public LinksGroup getLinksGroup() {
    Long __key = this.linksGroupId;
    if (linksGroup__resolvedKey == null
            || !linksGroup__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        LinksGroupDao targetDao = daoSession.getLinksGroupDao();
        LinksGroup linksGroupNew = targetDao.load(__key);
        synchronized (this) {
            linksGroup = linksGroupNew;
            linksGroup__resolvedKey = __key;
        }
    }
    return linksGroup;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 987654691)
public void setLinksGroup(LinksGroup linksGroup) {
    synchronized (this) {
        this.linksGroup = linksGroup;
        linksGroupId = linksGroup == null ? null : linksGroup.getId();
        linksGroup__resolvedKey = linksGroupId;
    }
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 134140098)
public List<LinkUrlItem> getLinkUrlItems() {
    if (linkUrlItems == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        LinkUrlItemDao targetDao = daoSession.getLinkUrlItemDao();
        List<LinkUrlItem> linkUrlItemsNew = targetDao
                ._queryLink_LinkUrlItems(id);
        synchronized (this) {
            if (linkUrlItems == null) {
                linkUrlItems = linkUrlItemsNew;
            }
        }
    }
    return linkUrlItems;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 1753104961)
public synchronized void resetLinkUrlItems() {
    linkUrlItems = null;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 892186495)
public List<LinkMapItem> getLinkMapItems() {
    if (linkMapItems == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        LinkMapItemDao targetDao = daoSession.getLinkMapItemDao();
        List<LinkMapItem> linkMapItemsNew = targetDao
                ._queryLink_LinkMapItems(id);
        synchronized (this) {
            if (linkMapItems == null) {
                linkMapItems = linkMapItemsNew;
            }
        }
    }
    return linkMapItems;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 1813508708)
public synchronized void resetLinkMapItems() {
    linkMapItems = null;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 467843949)
public List<LinkImgItem> getLinkImgItems() {
    if (linkImgItems == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        LinkImgItemDao targetDao = daoSession.getLinkImgItemDao();
        List<LinkImgItem> linkImgItemsNew = targetDao
                ._queryLink_LinkImgItems(id);
        synchronized (this) {
            if (linkImgItems == null) {
                linkImgItems = linkImgItemsNew;
            }
        }
    }
    return linkImgItems;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 1633232500)
public synchronized void resetLinkImgItems() {
    linkImgItems = null;
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}

/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 127260804)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getLinkDao() : null;
}
}
