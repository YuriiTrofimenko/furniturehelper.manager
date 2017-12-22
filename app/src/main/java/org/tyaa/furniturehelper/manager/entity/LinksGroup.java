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
}
