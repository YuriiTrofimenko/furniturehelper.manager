package org.tyaa.fhelpermodel.interfaces;

import androidx.databinding.ObservableArrayList;
import android.view.View;

/**
 * Created by yurii on 30.11.17.
 */

public interface ISubLinkList {

    void setSub_link(ISubLink subLink);
    void setSub_link_list(ObservableArrayList<ISubLink> subLinks);
    void add(View view);
    void remove(View view);
    Integer getSelectedItemPosition();
    void setSelectedItemPosition(Integer selectedItemPosition);
}
