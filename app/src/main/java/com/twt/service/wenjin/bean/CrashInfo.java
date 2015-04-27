package com.twt.service.wenjin.bean;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by M on 2015/4/20.
 */
@Table(name = "CrashInfos", id = BaseColumns._ID)
public class CrashInfo extends Model {

    @Column(name = "Detail")
    public String detail;

    public CrashInfo() {
        super();
    }

    public CrashInfo(String detail) {
        super();
        this.detail = detail;
    }
}
