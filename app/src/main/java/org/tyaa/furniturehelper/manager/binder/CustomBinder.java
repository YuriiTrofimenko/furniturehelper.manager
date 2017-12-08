package org.tyaa.furniturehelper.manager.binder;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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


    /*@BindingAdapter({"app:onLongClick"})
    public static void bindOnClick(
            View view
            , View.OnClickListener clickListener
            , boolean clickable) {

        view.setOnClickListener(clickListener);
        view.setClickable(clickable);
    }*/
}
