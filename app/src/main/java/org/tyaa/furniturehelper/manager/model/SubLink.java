package org.tyaa.furniturehelper.manager.model;

import android.graphics.drawable.Drawable;

import org.greenrobot.greendao.annotation.Entity;
import org.tyaa.furniturehelper.manager.model.interfaces.ISubLink;

/**
 * Created by yurii on 29.11.17.
 */

public class SubLink implements ISubLink {

    public Long id;
    public String text;
    public String link;
    public Drawable drawable;


    @Override
    public Long getId() {
        return id;
    }
}
