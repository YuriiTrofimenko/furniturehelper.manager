package org.tyaa.furniturehelper.manager.entity;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import org.tyaa.furniturehelper.manager.entity.LinkUrlItem;
import org.tyaa.furniturehelper.manager.entity.LinkUrlItemDao;

public class LinkUrlItemTest extends AbstractDaoTestLongPk<LinkUrlItemDao, LinkUrlItem> {

    public LinkUrlItemTest() {
        super(LinkUrlItemDao.class);
    }

    @Override
    protected LinkUrlItem createEntity(Long key) {
        LinkUrlItem entity = new LinkUrlItem();
        entity.setId(key);
        entity.setLink();
        entity.setGuid();
        return entity;
    }

}
