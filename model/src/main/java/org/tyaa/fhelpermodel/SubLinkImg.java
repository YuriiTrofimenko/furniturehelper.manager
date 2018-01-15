package org.tyaa.fhelpermodel;

import android.graphics.drawable.Drawable;

import org.tyaa.fhelpermodel.interfaces.ISubLink;

/**
 * Created by yurii on 29.11.17.
 */

public class SubLinkImg implements ISubLink {

    public Long id;
    public Drawable drawable;
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
