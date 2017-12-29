package org.tyaa.furniturehelper.manager.model;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by yurii on 29.11.17.
 */

public class LinkListItem {

    //private static Long count = 0L;

    private Long id;
    public String title;
    public Drawable drawable;
    public boolean checked;
    public SubLinkList subLinks;

    public LinkListItem() {

        //id = count;
        //count++;
    }

    public LinkListItem(Long id, String title, Drawable drawable, boolean checked, SubLinkList subLinks) {

        //id = count;
        //count++;
        this.id = id;
        this.title = title;
        this.drawable = drawable;
        this.checked = checked;
        this.subLinks = subLinks;
    }

    public Long getId() {
        return id;
    }
}
