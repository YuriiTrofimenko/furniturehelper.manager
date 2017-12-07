package org.tyaa.furniturehelper.manager.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.databinding.LinkListItemBinding;
import org.tyaa.furniturehelper.manager.model.LinkListItem;
import org.tyaa.furniturehelper.manager.viewmodel.LinkListViewModel;

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
        Log.d("MySpy", "bind!");
        binding.setItem(mList.get(i));

        binding.getRoot().setOnClickListener(v -> {

            Log.d("MySpy", (String) ((TextView)v.findViewById(R.id.linkListItemTitle)).getText());
            Toast.makeText(
                    v.getContext()
                    , ((TextView)v.findViewById(R.id.linkListItemTitle)).getText(), Toast.LENGTH_LONG);
        });



        /*binding.setOnLinkListItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        v.getContext()
                        , ((TextView)v).getText(), Toast.LENGTH_LONG);
            }
        });*/
        //binding.setViewModel(new LinkListViewModel());

        /*view.setOnLongClickListener((View v) -> {

            Toast.makeText(
                    view.getContext()
                    , ((TextView)view.findViewById(R.id.linkListItemTitle)).getText(), Toast.LENGTH_LONG);
            return true;
        });*/

        return binding.getRoot();
    }
}
