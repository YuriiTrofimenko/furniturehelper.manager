package org.tyaa.furniturehelper.manager.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import org.tyaa.furniturehelper.manager.entity.interfaces.ILinkItem;

/**
 * Сущность "группа ссылок"
 * Created by yurii on 15.12.17.
 */

@Entity(
        active = true,
        nameInDb = "LinksGroup"
)
public class LinksGroup {

    @Id(autoincrement = true)
    private Long id;

    /**
     * Заголовок группы
     * */
    @Property(nameInDb = "title")
    @NotNull
    private String title;

    /**
     * Включить ли данную группу ссылок в набор для отправки заказчику
     * */
    @Property(nameInDb = "checked")
    @NotNull
    private boolean checked;

    /**
     * Икона группы
     * */
    @Property(nameInDb = "drawable")
    @NotNull
    private String drawable;

    /**
     * Список элементов группы (тексты)
     * */
    @ToMany(referencedJoinProperty = "linksGroupId")
    public List<LinkTextItem> linkTextItems;

    /**
     * Список элементов группы (простые ссылки)
     * */
    @ToMany(referencedJoinProperty = "linksGroupId")
    public List<LinkUrlItem> linkUrlItems;

    /**
     * Список элементов группы (google map links)
     * */
    @ToMany(referencedJoinProperty = "linksGroupId")
    public List<LinkMapItem> linkMapItems;

    /**
     * Список элементов группы (images)
     * */
    @ToMany(referencedJoinProperty = "linksGroupId")
    public List<LinkImgItem> linkImgItems;

/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;

/** Used for active entity operations. */
@Generated(hash = 962927068)
private transient LinksGroupDao myDao;

@Generated(hash = 1960125821)
public LinksGroup(Long id, @NotNull String title, boolean checked,
        @NotNull String drawable) {
    this.id = id;
    this.title = title;
    this.checked = checked;
    this.drawable = drawable;
}

@Generated(hash = 1757250749)
public LinksGroup() {
}

public Long getId() {
    return this.id;
}

public void setId(Long id) {
    this.id = id;
}

public String getTitle() {
    return this.title;
}

public void setTitle(String title) {
    this.title = title;
}

public boolean getChecked() {
    return this.checked;
}

public void setChecked(boolean checked) {
    this.checked = checked;
}

public String getDrawable() {
    return this.drawable;
}

public void setDrawable(String drawable) {
    this.drawable = drawable;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 1676323551)
public List<LinkTextItem> getLinkTextItems() {
    if (linkTextItems == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        LinkTextItemDao targetDao = daoSession.getLinkTextItemDao();
        List<LinkTextItem> linkTextItemsNew = targetDao
                ._queryLinksGroup_LinkTextItems(id);
        synchronized (this) {
            if (linkTextItems == null) {
                linkTextItems = linkTextItemsNew;
            }
        }
    }
    return linkTextItems;
}

/** Resets a to-many relationship, making the next get call to query for a fresh result. */
@Generated(hash = 449404389)
public synchronized void resetLinkTextItems() {
    linkTextItems = null;
}

/**
 * To-many relationship, resolved on first access (and after reset).
 * Changes to to-many relations are not persisted, make changes to the target entity.
 */
@Generated(hash = 1605829111)
public List<LinkUrlItem> getLinkUrlItems() {
    if (linkUrlItems == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        LinkUrlItemDao targetDao = daoSession.getLinkUrlItemDao();
        List<LinkUrlItem> linkUrlItemsNew = targetDao
                ._queryLinksGroup_LinkUrlItems(id);
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
@Generated(hash = 1673273344)
public List<LinkMapItem> getLinkMapItems() {
    if (linkMapItems == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        LinkMapItemDao targetDao = daoSession.getLinkMapItemDao();
        List<LinkMapItem> linkMapItemsNew = targetDao
                ._queryLinksGroup_LinkMapItems(id);
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
@Generated(hash = 1819665971)
public List<LinkImgItem> getLinkImgItems() {
    if (linkImgItems == null) {
        final DaoSession daoSession = this.daoSession;
        if (daoSession == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        LinkImgItemDao targetDao = daoSession.getLinkImgItemDao();
        List<LinkImgItem> linkImgItemsNew = targetDao
                ._queryLinksGroup_LinkImgItems(id);
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
@Generated(hash = 1361419077)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getLinksGroupDao() : null;
}
    
}
