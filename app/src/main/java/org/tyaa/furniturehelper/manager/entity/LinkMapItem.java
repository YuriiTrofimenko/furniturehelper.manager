package org.tyaa.furniturehelper.manager.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToOne;
import org.tyaa.furniturehelper.manager.entity.interfaces.ILinkItem;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

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

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 1344271472)
private transient LinkMapItemDao myDao;

@Generated(hash = 250400953)
public LinkMapItem(Long id, @NotNull String link, Long linkId) {
    this.id = id;
    this.link = link;
    this.linkId = linkId;
}

@Generated(hash = 212292580)
public LinkMapItem() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getLink() {
    return this.link;
}

public void setLink(String link) {
    this.link = link;
}

public Long getLinkId() {
    return this.linkId;
}

public void setLinkId(Long linkId) {
    this.linkId = linkId;
}

@Generated(hash = 1853227027)
private transient Long parentLink__resolvedKey;

/** To-one relationship, resolved on first access. */
@Generated(hash = 1061151848)
public Link getParentLink() {
    Long __key = this.linkId;
    if (parentLink__resolvedKey == null
            || !parentLink__resolvedKey.equals(__key)) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        LinkDao targetDao = daoSession.getLinkDao();
        Link parentLinkNew = targetDao.load(__key);
        synchronized (this) {
            parentLink = parentLinkNew;
            parentLink__resolvedKey = __key;
        }
    }
    return parentLink;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 2073754214)
public void setParentLink(Link parentLink) {
    synchronized (this) {
        this.parentLink = parentLink;
        linkId = parentLink == null ? null : parentLink.getId();
        parentLink__resolvedKey = linkId;
    }
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
@Generated(hash = 993973150)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getLinkMapItemDao() : null;
}
}
