package com.yonghui.miniPocket.ui.load;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;

import com.yonghui.miniPocket.db.SqliteManage;

import java.io.File;

/**
 * Created by Yonghui Rao
 */
public class MyApplication extends Application {
    public String CONFDIR;
    public static Context mContext;

    @Override
    public void onCreate() {
        CONFDIR = this.getFilesDir() + "/config";
        makeConfigDir();
        initTime();
        mContext = this;
        super.onCreate();
    }

    private void initTime() {
        if (!SqliteManage.getInstance(this).isExitInTable("time", "time=?", new String[]{"firsttime"})) {
            ContentValues values = new ContentValues();
            values.put("time", "firsttime");
            values.put("value", System.currentTimeMillis());
            SqliteManage.getInstance(this).insertItem("time", values);
        }

        if (!SqliteManage.getInstance(this).isExitInTable("time", "time=?", new String[]{"bdtime"})) {
            ContentValues values = new ContentValues();
            values.put("time", "bdtime");
            values.put("value", 0);
            SqliteManage.getInstance(this).insertItem("time", values);
        }
    }

    private void makeConfigDir() {
        File file = new File(CONFDIR);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
}
