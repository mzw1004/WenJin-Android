package com.twt.service.wenjin.event;

/**
 * Created by M on 2015/3/22.
 */
public class DrawerItemClickedEvent {

    private int mPosition;

    public DrawerItemClickedEvent(int position) {
        this.mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}
