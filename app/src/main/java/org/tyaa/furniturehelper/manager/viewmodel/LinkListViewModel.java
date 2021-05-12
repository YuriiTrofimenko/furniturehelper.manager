package org.tyaa.furniturehelper.manager.viewmodel;

import android.content.Intent;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.tyaa.furniturehelper.manager.LinksEditActivity;
import org.tyaa.furniturehelper.manager.R;

/**
 * Created by yurii on 07.12.17.
 */

public class LinkListViewModel {//extends BaseObservable {

    /*@Bindable
    public View.OnClickListener setOnLinkListItemClick(){

        //Intent intent = new Intent(view.getContext(), LinksEditActivity.class);

        //view.findViewById(R.id.linkListItemTitle);
        Toast.makeText(
                view.getContext()
                , ((TextView)view.findViewById(R.id.linkListItemTitle)).getText(), Toast.LENGTH_LONG);
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        v.getContext()
                        , ((TextView)v).getText(), Toast.LENGTH_LONG);
            }
        };
    }*/

    /*public void onLinkListItemClick(View v){

        Toast.makeText(
                v.getContext()
                , ((TextView)v).getText(), Toast.LENGTH_LONG);
    }*/
}
