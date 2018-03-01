package org.tyaa.furniturehelper.manager.entity;

import org.greenrobot.greendao.test.AbstractDaoTestLongPk;

import org.tyaa.furniturehelper.manager.entity.LinksGroup;
import org.tyaa.furniturehelper.manager.entity.LinksGroupDao;

public class LinksGroupTest extends AbstractDaoTestLongPk<LinksGroupDao, LinksGroup> {

    public LinksGroupTest() {
        super(LinksGroupDao.class);
    }

    @Override
    protected LinksGroup createEntity(Long key) {
        LinksGroup entity = new LinksGroup();
        entity.setId(key);
        entity.setTitle();
        entity.setChecked();
        entity.setDrawable();
        return entity;
    }

}
