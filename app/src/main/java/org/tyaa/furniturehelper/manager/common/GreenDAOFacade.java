package org.tyaa.furniturehelper.manager.common;

import org.greenrobot.greendao.query.Query;
import org.tyaa.furniturehelper.manager.R;
import org.tyaa.furniturehelper.manager.entity.DaoSession;
import org.tyaa.furniturehelper.manager.entity.Link;
import org.tyaa.furniturehelper.manager.entity.LinkDao;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.entity.LinksGroupDao;

import java.util.List;

/**
 * Created by yurii on 15.12.17.
 */

public class GreenDAOFacade {

    private DaoSession mDaoSession;

    private LinksGroupDao mLinksGroupDao;
    private LinkDao mLinkDao;

    private Query<LinksGroup> mLinksGroupQuery;

    //public List<LinksGroup> mLinksGroupList;

    public GreenDAOFacade() {

        mDaoSession = CurrentApplication.getDaoSession();

        mLinksGroupDao = mDaoSession.getLinksGroupDao();
        mLinkDao = mDaoSession.getLinkDao();

        mLinksGroupQuery =
                mLinksGroupDao.queryBuilder()
                        .orderAsc(LinksGroupDao.Properties.Title)
                        .build();
    }

    /* LinksGroups */

    public List<LinksGroup> getAllLinksGroups(){

        return mLinksGroupQuery.list();
    }

    public LinksGroup createLinksGroup(String _title, Boolean _checked, String _drawable){

        LinksGroup linksGroup = new LinksGroup();
        linksGroup.setTitle(_title);
        linksGroup.setChecked(_checked);
        linksGroup.setDrawable(_drawable);
        Long id = mLinksGroupDao.insert(linksGroup);
        return mLinksGroupDao.load(id);
    }

    /* Link */

    public Link createLink(String _text, String _link, String _drawable, Long LinksGroupId){

        Link link = new Link();
        link.setText(_text);
        //link.setLink(_link);
        //link.setDrawable(_drawable);
        Long id = mLinkDao.insert(link);
        return mLinkDao.loadDeep(id);
    }
}
