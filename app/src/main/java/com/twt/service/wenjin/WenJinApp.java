package com.twt.service.wenjin;

import android.app.Application;
import android.content.Context;

import com.loopj.android.http.PersistentCookieStore;
import com.twt.service.wenjin.api.ApiClient;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by M on 2015/3/19.
 */
public class WenJinApp extends Application {

    private static Context sContext;
    private static PersistentCookieStore sCookieStore;

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);

        sContext = getApplicationContext();
        sCookieStore = new PersistentCookieStore(sContext);
        ApiClient.getInstance().setCookieStore(sCookieStore);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public ObjectGraph createScopedGraph(Object... modules) {
        return objectGraph.plus(modules);
    }

    public static Context getContext() {
        return sContext;
    }

    public static PersistentCookieStore getCookieStore() {
        return sCookieStore;
    }
}
