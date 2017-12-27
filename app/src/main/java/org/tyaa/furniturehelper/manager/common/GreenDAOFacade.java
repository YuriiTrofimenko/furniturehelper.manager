package org.tyaa.furniturehelper.manager.common;

import org.greenrobot.greendao.query.Query;
import org.tyaa.furniturehelper.manager.entity.DaoSession;
import org.tyaa.furniturehelper.manager.entity.LinkImgItem;
import org.tyaa.furniturehelper.manager.entity.LinkImgItemDao;
import org.tyaa.furniturehelper.manager.entity.LinkMapItem;
import org.tyaa.furniturehelper.manager.entity.LinkMapItemDao;
import org.tyaa.furniturehelper.manager.entity.LinkTextItem;
import org.tyaa.furniturehelper.manager.entity.LinkTextItemDao;
import org.tyaa.furniturehelper.manager.entity.LinkUrlItem;
import org.tyaa.furniturehelper.manager.entity.LinkUrlItemDao;
import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.entity.LinksGroupDao;
import org.tyaa.furniturehelper.manager.entity.interfaces.ILinkItem;

import java.util.List;

/**
 * Created by yurii on 15.12.17.
 */

public class GreenDAOFacade {

    private DaoSession mDaoSession;

    private LinksGroupDao mLinksGroupDao;
    //private LinkDao mLinkDao;
    private LinkTextItemDao mLinkTextItemDao;
    private LinkUrlItemDao mLinkUrlItemDao;
    private LinkMapItemDao mLinkMapItemDao;
    private LinkImgItemDao mLinkImgItemDao;

    private Query<LinksGroup> mLinksGroupQuery;

    //public List<LinksGroup> mLinksGroupList;

    public GreenDAOFacade() {

        mDaoSession = CurrentApplication.getDaoSession();

        mLinksGroupDao = mDaoSession.getLinksGroupDao();
        //mLinkDao = mDaoSession.getLinkDao();
        mLinkTextItemDao = mDaoSession.getLinkTextItemDao();
        mLinkUrlItemDao = mDaoSession.getLinkUrlItemDao();
        mLinkMapItemDao = mDaoSession.getLinkMapItemDao();
        mLinkImgItemDao = mDaoSession.getLinkImgItemDao();

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

    public void clearLinksGroups(){

        mLinksGroupDao.deleteAll();
    }

    /* Link */

    public void createLink(ILinkItem _linkItem){

        Long id = null;

        if (_linkItem instanceof LinkTextItem) {

            id = mLinkTextItemDao.insert((LinkTextItem)_linkItem);
        } else if (_linkItem instanceof LinkUrlItem) {

            id = mLinkUrlItemDao.insert((LinkUrlItem)_linkItem);
        } else if (_linkItem instanceof LinkMapItem) {

            id = mLinkMapItemDao.insert((LinkMapItem)_linkItem);
        } else /*if (_linkItem instanceof LinkImgItem)*/ {

            id = mLinkImgItemDao.insert((LinkImgItem)_linkItem);
        }
    }

    /*public Link createLink(String _text, String _link, String _drawable, Long LinksGroupId){

        Link link = new Link();
        link.setText(_text);
        //link.setLink(_link);
        //link.setDrawable(_drawable);
        Long id = mLinkDao.insert(link);
        return mLinkDao.loadDeep(id);
    }*/
}
