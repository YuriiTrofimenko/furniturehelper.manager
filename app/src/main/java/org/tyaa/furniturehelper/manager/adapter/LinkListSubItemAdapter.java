package org.tyaa.furniturehelper.manager.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.databinding.LinkBinding;
import org.tyaa.furniturehelper.manager.model.SubLink;

/**
 * Created by yurii on 30.11.17.
 */

public class LinkListSubItemAdapter extends BaseAdapter {

    private ObservableArrayList<SubLink> mList;
    private LayoutInflater mInflater;

    public LinkListSubItemAdapter(ObservableArrayList<SubLink> _list) {

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

        LinkBinding binding =
                DataBindingUtil.inflate(mInflater, R.layout.link, viewGroup, false);
        Log.d("MySpy", "bind!");
        binding.setItem(mList.get(i));

        return binding.getRoot();
    }
}
