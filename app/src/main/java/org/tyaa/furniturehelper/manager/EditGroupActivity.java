package org.tyaa.furniturehelper.manager;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.tyaa.furniturehelper.manager.common.Global;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditGroupActivity extends Activity {
    //actually it is index, not id
    public static final String GROUP_ID = "org.tyaa.furniturehelper.manager.AppCompatActivity.GroupId";
    public static final String GROUP_NAME = "org.tyaa.furniturehelper.manager.AppCompatActivity.GroupName";

    @BindView(R.id.etGroupName)
    EditText etGroupName;
    private int groupIndex;//-1 if we need to create a new group

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_group);

        ButterKnife.bind(this);

        if (savedInstanceState == null){
            Intent intent = getIntent();

            //edit group
            if (intent.hasExtra(GROUP_ID)) {
                groupIndex = intent.getIntExtra(GROUP_ID, 0);
                String groupName = Global.LINK_LIST.mLinkItemList.get(groupIndex).title;
                etGroupName.setText(groupName);
                setTitle(R.string.titleEditGroup);
            }else
            {//create new group
                groupIndex = -1;
                setTitle(R.string.titleCreateGroup);
            }
        }
        else//restore state
        {
            groupIndex = savedInstanceState.getInt(GROUP_ID);
            String groupName = savedInstanceState.getString(GROUP_NAME);
            etGroupName.setText(groupName);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(GROUP_ID, groupIndex);
        outState.putString(GROUP_NAME, etGroupName.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.bSave)
    void onSaveClick(View view) {
        String st = etGroupName.getText().toString();

        if (st.isEmpty()){
            Toast.makeText(this, R.string.sFieldIsBlank, Toast.LENGTH_SHORT).show();
        }
        else{
            Intent i = new Intent();
            i.putExtra(GROUP_ID, groupIndex);
            i.putExtra(GROUP_NAME, st);

            setResult(RESULT_OK, i);
            finish();
        }
    }
}
