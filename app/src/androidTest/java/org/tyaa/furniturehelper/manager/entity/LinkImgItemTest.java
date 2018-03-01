package org.tyaa.furniturehelper.manager.entity;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import org.tyaa.furniturehelper.manager.entity.LinkImgItem;
import org.tyaa.furniturehelper.manager.entity.LinkImgItemDao;

public class LinkImgItemTest extends AbstractDaoTestLongPk<LinkImgItemDao, LinkImgItem> {

    public LinkImgItemTest() {
        super(LinkImgItemDao.class);
    }

    @Override
    protected LinkImgItem createEntity(Long key) {
        LinkImgItem entity = new LinkImgItem();
        entity.setId(key);
        entity.setDrawable();
        entity.setGuid();
        return entity;
    }

}
