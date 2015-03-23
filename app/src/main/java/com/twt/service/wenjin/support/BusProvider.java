package com.twt.service.wenjin.support;

import com.squareup.otto.Bus;

/**
 * Created by M on 2015/3/22.
 */
public class BusProvider {

    private static Bus sBus = new Bus();

    public static Bus getBusInstance() {
        return sBus;
    }
}
