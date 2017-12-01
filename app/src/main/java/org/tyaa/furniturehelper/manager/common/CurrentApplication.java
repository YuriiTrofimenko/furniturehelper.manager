package org.tyaa.furniturehelper.manager.common;

import android.app.Application;
import android.content.Context;

/**
 * Created by yurii on 30.11.17.
 */

public class CurrentApplication extends Application {

    private static Context mContext;

    public void onCreate() {

        super.onCreate();

        CurrentApplication.mContext = getApplicationContext();
    }

    public static Context getAppContext() {

        return CurrentApplication.mContext;
    }
}
