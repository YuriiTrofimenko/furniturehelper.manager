package org.tyaa.fhelpermodel;

import org.tyaa.fhelpermodel.interfaces.ISubLink;

/**
 * Created by yurii on 29.11.17.
 */

public class SubLinkLink implements ISubLink {

    public Long id;
    public String link;
    public String guid;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getGuid() {
        return guid;
    }
}
