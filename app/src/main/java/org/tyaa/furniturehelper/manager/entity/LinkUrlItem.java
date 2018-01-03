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
        nameInDb = "LinkUrlItem"
)
public class LinkUrlItem implements ILinkItem {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "link")
    @NotNull
    private String link;

    /**
     * GUID
     * */
    @Property(nameInDb = "guid")
    @NotNull
    private String guid;

    //ИД "Ссылки", к которой принадлежит данное прикрепление
    private Long linksGroupId;

    @ToOne(joinProperty = "linksGroupId")
    private LinksGroup linksGroup;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 471976578)
private transient LinkUrlItemDao myDao;

@Generated(hash = 1648849604)
public LinkUrlItem(Long id, @NotNull String link, @NotNull String guid,
        Long linksGroupId) {
    this.id = id;
    this.link = link;
    this.guid = guid;
    this.linksGroupId = linksGroupId;
}

@Generated(hash = 1534743304)
public LinkUrlItem() {
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

public String getGuid() {
    return this.guid;
}

public void setGuid(String guid) {
    this.guid = guid;
}

/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 189560364)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getLinkUrlItemDao() : null;
}
}
