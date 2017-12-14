package org.tyaa.furniturehelper.manager.common;

import android.databinding.ObservableArrayList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.model.LinkList;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

/**
 * Created by yurii on 30.11.17.
 */

public class Global {

    public static final LinkList LINK_LIST;
    public static ImageView selectedImageView;
    public static final Drawable EMPTY_DRAWABLE;
    public static int selectedSubLinkPos;

    static {

        LINK_LIST = new LinkList();
        LINK_LIST.setLink_list(Generator.getLinkListBasis());
        EMPTY_DRAWABLE =
                CurrentApplication.getAppContext()
                        .getResources()
                        .getDrawable(R.drawable.empty);
    }
}
