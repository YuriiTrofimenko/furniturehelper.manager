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

import org.tyaa.fhelpermodel.LinkListItem;
import org.tyaa.furniturehelper.manager.BusinessCardActivity;
import org.tyaa.furniturehelper.manager.LinksEditActivity;
import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.databinding.LinkListItemBinding;
/**
 * Created by yurii on 30.11.17.
 */

public class LinkListItemAdapter extends BaseAdapter {

    private ObservableArrayList<LinkListItem> mList;
    private LayoutInflater mInflater;

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

        if (mInflater == null) {

            mInflater = (LayoutInflater) viewGroup.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        LinkListItemBinding binding =
                DataBindingUtil.inflate(mInflater, R.layout.link_list_item, viewGroup, false);
        //Log.d("MySpy", "bind!");
        binding.setItem(mList.get(i));

        /*((ListView)viewGroup).setOnItemLongClickListener((parent, view1, position, id) -> {

            Log.d("pos = ", String.valueOf(position));
            return true;
        });*/

        binding.getRoot().setOnClickListener(v -> {

            //Log.d("MySpy", (String) ((TextView)v.findViewById(R.id.linkListItemTitle)).getText());
            Log.d("id = ", String.valueOf(mList.get(i).getId()));
            Intent intent = new Intent(v.getContext(), LinksEditActivity.class);
            /*intent.putExtra(
                    BusinessCardActivity.SELECTED_LINK_LIST_ITEM_TITLE
                    , ((TextView)v.findViewById(R.id.linkListItemTitle)).getText()
            );*/
            intent.putExtra(
                    BusinessCardActivity.SELECTED_LINK_LIST_ITEM_TITLE
                    , mList.get(i).getId()
            );
            ((BusinessCardActivity)v.getContext())
                    .startActivityForResult(intent, BusinessCardActivity.EDIT_GROUP_REQUEST);
        });

        return binding.getRoot();
    }
}
