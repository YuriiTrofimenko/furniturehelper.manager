package org.tyaa.furniturehelper.manager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.tyaa.furniturehelper.manager.receiver.CallReceiver;

public class BusinessCardActivity extends AppCompatActivity {

    private String mPhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_business_card);

        mPhoneNumber =
                getIntent().getStringExtra(CallReceiver.EXTRA_PHONE_NUMBER);
        Toast.makeText(this, mPhoneNumber,
                Toast.LENGTH_SHORT).show();
    }
}
