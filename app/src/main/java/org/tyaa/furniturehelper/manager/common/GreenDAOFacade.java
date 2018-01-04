package org.tyaa.furniturehelper.manager.common;

import org.greenrobot.greendao.query.Join;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
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

import java.util.ArrayList;
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

        QueryBuilder<LinksGroup> linksGroupQueryBuilder =
                mLinksGroupDao.queryBuilder();

        /*Join textItems =
                linksGroupQueryBuilder.join(
                        LinkTextItem.class, LinkTextItemDao.Properties.LinksGroupId
                );

        Join linkItems = linksGroupQueryBuilder.join(textItems, LinksGroupDao.Properties.Id,
                LinkUrlItem.class, LinkUrlItemDao.Properties.LinksGroupId);

        Join mapItems = linksGroupQueryBuilder.join(linkItems, LinksGroupDao.Properties.Id,
                LinkMapItem.class, LinkMapItemDao.Properties.LinksGroupId);

        Join imgItems = linksGroupQueryBuilder.join(mapItems, LinksGroupDao.Properties.Id,
                LinkImgItem.class, LinkImgItemDao.Properties.LinksGroupId);*/

        /*linksGroupQueryBuilder
                .join()
                .j
                .where(LinkTextItemDao.Properties.Id.isNotNull());*/
        mLinksGroupQuery =
                linksGroupQueryBuilder.orderAsc(LinksGroupDao.Properties.Title).build();

    }

    /* LinksGroups */

    public List<LinksGroup> getAllLinksGroups(){

        List<LinksGroup> linksGroups =
                mLinksGroupQuery.list();
        for (LinksGroup linksGroup : linksGroups) {

            if (linksGroup.linkTextItems == null) {

                linksGroup.linkTextItems =
                        new ArrayList<>();
            }
            List<LinkTextItem> textItems =
                getTextItemsById(linksGroup.getId());
            if (textItems != null) {

                linksGroup.linkTextItems.addAll(textItems);
            }

            if (linksGroup.linkUrlItems == null) {

                linksGroup.linkUrlItems =
                        new ArrayList<>();
            }
            List<LinkUrlItem> urlItems =
                    getUrlItemsById(linksGroup.getId());
            if (urlItems != null) {

                linksGroup.linkUrlItems.addAll(urlItems);
            }

            if (linksGroup.linkMapItems == null) {

                linksGroup.linkMapItems =
                        new ArrayList<>();
            }
            List<LinkMapItem> mapItems =
                    getMapItemsById(linksGroup.getId());
            if (mapItems != null) {

                linksGroup.linkMapItems.addAll(mapItems);
            }

            if (linksGroup.linkImgItems == null) {

                linksGroup.linkImgItems =
                        new ArrayList<>();
            }
            List<LinkImgItem> imgItems =
                    getImgItemsById(linksGroup.getId());
            if (imgItems != null) {

                linksGroup.linkImgItems.addAll(imgItems);
            }
        }
        return linksGroups;
    }

    public LinksGroup getLinksGroupById(Long _id)
    {
        return mLinksGroupDao.queryBuilder()
                .where(LinksGroupDao.Properties.Id.eq(_id))
                .build()
                .unique();
    }

    public LinksGroup createLinksGroup(String _title, Boolean _checked, String _drawable){

        LinksGroup linksGroup = new LinksGroup();
        linksGroup.setTitle(_title);
        linksGroup.setChecked(_checked);
        linksGroup.setDrawable(_drawable);
        Long id = mLinksGroupDao.insert(linksGroup);
        return mLinksGroupDao.load(id);
    }

    public void updateLinksGroup(LinksGroup _linksGroup)
    {

        mLinksGroupDao.update(_linksGroup);
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

    public void createLink(ILinkItem _linkItem, Long _groupId){

        Long id = null;

        if (_linkItem instanceof LinkTextItem) {

            LinkTextItem linkItem = (LinkTextItem) _linkItem;
            LinksGroup linksGroup = getLinksGroupById(_groupId);
            linkItem.setLinksGroup(linksGroup);
            id = mLinkTextItemDao.insert(linkItem);
            if (linksGroup.linkTextItems == null) {

                linksGroup.linkTextItems = new ArrayList<>();
            }
            linksGroup.linkTextItems.add(linkItem);
            mLinksGroupDao.update(linksGroup);
        } else if (_linkItem instanceof LinkUrlItem) {

            //id = mLinkUrlItemDao.insert((LinkUrlItem)_linkItem);
            LinkUrlItem linkItem = (LinkUrlItem) _linkItem;
            LinksGroup linksGroup = getLinksGroupById(_groupId);
            linkItem.setLinksGroup(linksGroup);
            id = mLinkUrlItemDao.insert(linkItem);
            if (linksGroup.linkUrlItems == null) {

                linksGroup.linkUrlItems = new ArrayList<>();
            }
            linksGroup.linkUrlItems.add(linkItem);
            mLinksGroupDao.update(linksGroup);
        } else if (_linkItem instanceof LinkMapItem) {

            //id = mLinkMapItemDao.insert((LinkMapItem)_linkItem);
            LinkMapItem linkItem = (LinkMapItem) _linkItem;
            LinksGroup linksGroup = getLinksGroupById(_groupId);
            linkItem.setLinksGroup(linksGroup);
            id = mLinkMapItemDao.insert(linkItem);
            if (linksGroup.linkMapItems == null) {

                linksGroup.linkMapItems = new ArrayList<>();
            }
            linksGroup.linkMapItems.add(linkItem);
            mLinksGroupDao.update(linksGroup);
        } else /*if (_linkItem instanceof LinkImgItem)*/ {

            //id = mLinkImgItemDao.insert((LinkImgItem)_linkItem);
            LinkImgItem linkItem = (LinkImgItem) _linkItem;
            LinksGroup linksGroup = getLinksGroupById(_groupId);
            linkItem.setLinksGroup(linksGroup);
            id = mLinkImgItemDao.insert(linkItem);
            if (linksGroup.linkImgItems == null) {

                linksGroup.linkImgItems = new ArrayList<>();
            }
            linksGroup.linkImgItems.add(linkItem);
            mLinksGroupDao.update(linksGroup);
        }
    }

    public List<LinkTextItem> getTextItemsById(Long _id)
    {
        return mLinkTextItemDao.queryBuilder()
                .where(LinkTextItemDao.Properties.LinksGroupId.eq(_id))
                .build()
                .list();
    }

    public List<LinkUrlItem> getUrlItemsById(Long _id)
    {
        return mLinkUrlItemDao.queryBuilder()
                .where(LinkUrlItemDao.Properties.LinksGroupId.eq(_id))
                .build()
                .list();
    }

    public List<LinkMapItem> getMapItemsById(Long _id)
    {
        return mLinkMapItemDao.queryBuilder()
                .where(LinkMapItemDao.Properties.LinksGroupId.eq(_id))
                .build()
                .list();
    }

    public List<LinkImgItem> getImgItemsById(Long _id)
    {
        return mLinkImgItemDao.queryBuilder()
                .where(LinkImgItemDao.Properties.LinksGroupId.eq(_id))
                .build()
                .list();
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
