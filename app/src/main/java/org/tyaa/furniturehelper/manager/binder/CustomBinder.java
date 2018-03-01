package org.tyaa.furniturehelper.manager.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.widget.ListView;

import org.tyaa.fhelpermodel.LinkListItem;
import org.tyaa.fhelpermodel.interfaces.ISubLink;
import org.tyaa.furniturehelper.manager.adapter.LinkListItemAdapter;
import org.tyaa.furniturehelper.manager.adapter.LinkListSubItemAdapter;

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
    public static void bindSubList(ListView view, ObservableArrayList<ISubLink> list) {
        LinkListSubItemAdapter adapter = new LinkListSubItemAdapter(list);
        view.setAdapter(adapter);
    }


    /*@BindingAdapter({"app:onLongClick"})
    public static void bindOnClick(
            View view
            , View.OnClickListener clickListener
            , boolean clickable) {

        view.setOnClickListener(clickListener);
        view.setClickable(clickable);
    }*/
}
