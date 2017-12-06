package org.tyaa.furniturehelper.manager.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.widget.ListView;

import org.tyaa.furniturehelper.manager.adapter.LinkListItemAdapter;
import org.tyaa.furniturehelper.manager.adapter.LinkListSubItemAdapter;
import org.tyaa.furniturehelper.manager.model.SubLink;
import org.tyaa.furniturehelper.manager.model.LinkListItem;

/**
 * Created by yurii on 30.11.17.
 */

public class CustomBinder {

    @BindingAdapter({"link_list"})
    public static void bindList(ListView view, ObservableArrayList<LinkListItem> list) {

        LinkListItemAdapter adapter = new LinkListItemAdapter(list);
        view.setAdapter(adapter);
    }

    @BindingAdapter({"sub_link_list"})
    public static void bindSubList(ListView view, ObservableArrayList<SubLink> list) {

        LinkListSubItemAdapter adapter = new LinkListSubItemAdapter(list);
        view.setAdapter(adapter);
    }
}
