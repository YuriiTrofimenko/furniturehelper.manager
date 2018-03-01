package org.tyaa.furniturehelper.manager.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
//import android.widget.ListView;

import org.tyaa.fhelpermodel.LinkListItem;
import org.tyaa.furniturehelper.manager.BusinessCardActivity;
//import org.tyaa.furniturehelper.manager.LinksEditActivity;
import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.databinding.LinkListItemBinding;
/**
 * Created by yurii on 30.11.17.
 */

public class LinkListItemAdapter extends BaseAdapter {
    private ObservableArrayList<LinkListItem> mList;
    private LayoutInflater mInflater;
    public static final String MENU_ITEM_INDEX = "menuindex";

    public LinkListItemAdapter(ObservableArrayList<LinkListItem> _list) {
        mList = _list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //View vv = view;

        if (mInflater == null) {
            Object o = viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mInflater = (LayoutInflater) o;
          //  vv = (View)o;
        }

        LinkListItemBinding binding =
                DataBindingUtil.inflate(mInflater, R.layout.link_list_item, viewGroup, false);
       // Log.d("MySpy", "bind!");
        binding.setItem(mList.get(i));

        binding.getRoot().setOnClickListener(v -> {
           BusinessCardActivity activity = (BusinessCardActivity)v.getContext();
           Intent intent = new Intent();
           intent.putExtra(MENU_ITEM_INDEX, i);
           activity.setIntent(intent);
           activity.openContextMenu(viewGroup);
        });

        CheckBox ch = (CheckBox) binding.getRoot().findViewById(R.id.chb);
        ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mList.get(i).checked = ch.isChecked();
            }
        });

        return binding.getRoot();
    }
}
