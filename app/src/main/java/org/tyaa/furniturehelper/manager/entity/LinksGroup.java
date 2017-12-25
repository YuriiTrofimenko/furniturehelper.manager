package org.tyaa.furniturehelper.manager.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * Created by yurii on 15.12.17.
 */

@Entity(
        active = true,
        nameInDb = "LinksGroup"
)
public class LinksGroup {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "title")
    @NotNull
    private String title;

    @Property(nameInDb = "checked")
    @NotNull
    private boolean checked;

    @Property(nameInDb = "drawable")
    @NotNull
    private String drawable;

    @ToMany(referencedJoinProperty = "linksGroupId")
    private List<Link> links;

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
    @Generated(hash = 15676831)
    public List<Link> getLinks() {
        if (links == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LinkDao targetDao = daoSession.getLinkDao();
            List<Link> linksNew = targetDao._queryLinksGroup_Links(id);
            synchronized (this) {
                if (links == null) {
                    links = linksNew;
                }
            }
        }
        return links;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1715344246)
    public synchronized void resetLinks() {
        links = null;
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
