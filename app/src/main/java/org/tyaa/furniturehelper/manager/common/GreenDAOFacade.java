package org.tyaa.furniturehelper.manager.common;

import org.greenrobot.greendao.query.Query;
import org.tyaa.furniturehelper.manager.entity.DaoSession;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.entity.LinksGroupDao;

import java.util.List;

/**
 * Created by yurii on 15.12.17.
 */

public class GreenDAOFacade {

    private DaoSession mDaoSession;
    private LinksGroupDao mLinksGroupDao;
    private Query<LinksGroup> mLinksGroupQuery;

    public List<LinksGroup> mLinksGroupList;

    public GreenDAOFacade() {

        mDaoSession = CurrentApplication.getDaoSession();
        mLinksGroupDao = mDaoSession.getLinksGroupDao();
    }

    //public boolean dbDataExists
}
