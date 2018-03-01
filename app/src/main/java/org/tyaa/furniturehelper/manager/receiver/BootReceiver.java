package org.tyaa.furniturehelper.manager.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.tyaa.furniturehelper.manager.PermissionActivity;
import org.tyaa.furniturehelper.manager.service.DemoService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");
        //Intent demoIntent = new Intent(context, DemoService.class);
        //context.startService(demoIntent);

        Intent checkPermissionIntent = new Intent(context, PermissionActivity.class);
        checkPermissionIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(checkPermissionIntent);
    }
}
