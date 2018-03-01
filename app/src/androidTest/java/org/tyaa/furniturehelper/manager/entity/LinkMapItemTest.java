package org.tyaa.furniturehelper.manager.entity;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import org.tyaa.furniturehelper.manager.entity.LinkMapItem;
import org.tyaa.furniturehelper.manager.entity.LinkMapItemDao;

public class LinkMapItemTest extends AbstractDaoTestLongPk<LinkMapItemDao, LinkMapItem> {

    public LinkMapItemTest() {
        super(LinkMapItemDao.class);
    }

    @Override
    protected LinkMapItem createEntity(Long key) {
        LinkMapItem entity = new LinkMapItem();
        entity.setId(key);
        entity.setLink();
        entity.setGuid();
        return entity;
    }

}
