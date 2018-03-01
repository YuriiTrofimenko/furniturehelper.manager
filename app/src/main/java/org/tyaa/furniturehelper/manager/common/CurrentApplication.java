package org.tyaa.furniturehelper.manager.common;

import android.app.Application;
import android.content.Context;

import org.greenrobot.greendao.database.Database;
import org.tyaa.furniturehelper.manager.entity.DaoMaster;
import org.tyaa.furniturehelper.manager.entity.DaoSession;

/**
 * Главный класс приложения
 * Created by yurii on 30.11.17.
 */

public class CurrentApplication extends Application {

    //Контекст всего приложения, доступен глобально
    private static Context mContext;
    //Сеанс работы с БД, доступен глобально
    private static DaoSession mDaoSession;

    public void onCreate() {
        super.onCreate();

        CurrentApplication.mContext = getApplicationContext();

        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper(this, "business-card-db");
        Database db = helper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
        //DaoMaster.dropAllTables(db, true);
        //DaoMaster.createAllTables(db, true);
    }

    public static Context getAppContext() {
        return CurrentApplication.mContext;
    }

    public static DaoSession getDaoSession() {
        return mDaoSession;
    }
}
