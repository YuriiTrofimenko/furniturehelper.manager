package org.tyaa.furniturehelper.manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.tyaa.furniturehelper.manager.receiver.CallReceiver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button fakeCallButton = (Button) findViewById(R.id.fakeCallButton);
        fakeCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainActivityIntent = new Intent(MainActivity.this, BusinessCardActivity.class);
                mainActivityIntent.putExtra(CallReceiver.EXTRA_PHONE_NUMBER, "000");
                startActivity(mainActivityIntent);
            }
        });
    }
}
