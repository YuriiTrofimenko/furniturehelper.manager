package org.tyaa.furniturehelper.manager.model;

import android.graphics.drawable.Drawable;

import java.util.List;

/**
 * Created by yurii on 29.11.17.
 */

public class LinkListItem {

    public String title;
    public Drawable drawable;
    public boolean checked;
    public List<Link> links;

    public LinkListItem() {
    }

    public LinkListItem(String title, Drawable drawable, boolean checked, List<Link> links) {
        this.title = title;
        this.drawable = drawable;
        this.checked = checked;
        this.links = links;
    }
}
