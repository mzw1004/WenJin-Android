package com.twt.service.wenjin;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

/**
 * Created by M on 2015/3/19.
 */
public class WenJinApp extends Application {

    private ObjectGraph objectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        objectGraph = ObjectGraph.create(getModules().toArray());
        objectGraph.inject(this);
    }

    private List<Object> getModules() {
        return Arrays.<Object>asList(new AppModule(this));
    }

    public ObjectGraph createScopedGraph(Object... modules) {
        return objectGraph.plus(modules);
    }
}
