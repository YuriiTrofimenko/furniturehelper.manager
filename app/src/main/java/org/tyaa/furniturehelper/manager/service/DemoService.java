package org.tyaa.furniturehelper.manager.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class DemoService extends Service {
    public DemoService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the org.tyaa.furniturehelper.manager.service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {

        super.onCreate();

        Toast.makeText(this, "Служба создана",
                Toast.LENGTH_SHORT).show();
    }
}
