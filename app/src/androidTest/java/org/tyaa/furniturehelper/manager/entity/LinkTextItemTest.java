package org.tyaa.furniturehelper.manager.entity;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import org.tyaa.furniturehelper.manager.entity.LinkTextItem;
import org.tyaa.furniturehelper.manager.entity.LinkTextItemDao;

public class LinkTextItemTest extends AbstractDaoTestLongPk<LinkTextItemDao, LinkTextItem> {

    public LinkTextItemTest() {
        super(LinkTextItemDao.class);
    }

    @Override
    protected LinkTextItem createEntity(Long key) {
        LinkTextItem entity = new LinkTextItem();
        entity.setId(key);
        entity.setText();
        entity.setGuid();
        return entity;
    }

}
