package org.tyaa.furniturehelper.manager.model;

import android.graphics.drawable.Drawable;

import org.tyaa.furniturehelper.manager.model.interfaces.ISubLink;

/**
 * Created by yurii on 29.11.17.
 */

public class SubLinkText implements ISubLink {

    public Long id;
    public String text;
    public String guid;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getGuid() {
        return guid;
    }
}
