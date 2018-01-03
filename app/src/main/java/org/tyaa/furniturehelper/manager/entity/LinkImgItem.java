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
 * Attachment: image
 * Created by yurii on 21.12.17.
 */

@Entity(
        active = true,
        nameInDb = "LinkImgItem"
)
public class LinkImgItem implements ILinkItem {

    @Id(autoincrement = true)
    private Long id;

    /**
     * Изображение
     * */
    @Property(nameInDb = "drawable")
    @NotNull
    private String drawable;

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
@Generated(hash = 2022721852)
private transient LinkImgItemDao myDao;

@Generated(hash = 171562668)
public LinkImgItem(Long id, @NotNull String drawable, @NotNull String guid,
        Long linksGroupId) {
    this.id = id;
    this.drawable = drawable;
    this.guid = guid;
    this.linksGroupId = linksGroupId;
}

@Generated(hash = 2104551720)
public LinkImgItem() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getDrawable() {
    return this.drawable;
}

public void setDrawable(String drawable) {
    this.drawable = drawable;
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
@Generated(hash = 1720239448)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getLinkImgItemDao() : null;
}
}
