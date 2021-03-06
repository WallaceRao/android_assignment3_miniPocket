package com.yonghui.miniPocket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;

/**
 * Created by  Yonghui Rao
 */
public class SqliteManage {
    private static SqliteManage mInstance;
    private File mFile;

    private SqliteManage(Context context) {
        String path;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + File.separator + "天天记";
        } else {
            path = context.getFilesDir() + File.separator + "data";
        }
        boolean success = new File(path).mkdirs();
        mFile = new File(path, "data.db");

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mFile, null);
        db.execSQL("create table if not exists inout(_id INTEGER PRIMARY KEY AUTOINCREMENT,year " +
                "INTEGER,month INTEGER,day INTEGER,week INTEGER,money varchar(20)," +
                "inout varchar(20),class varchar(20),count varchar(20),time varchar(20),other varchar(60))");
        db.execSQL("create table if not exists count(_id INTEGER PRIMARY KEY AUTOINCREMENT,count varchar(20),money varchar(20))");
        db.execSQL("create table if not exists time(_id INTEGER PRIMARY KEY AUTOINCREMENT,time varchar(20),value varchar(20))");
        db.close();
    }

    public static SqliteManage getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SqliteManage.class) {
                if (mInstance == null) {
                    mInstance = new SqliteManage(context);
                }
            }
        }
        return mInstance;
    }

    public boolean isExitInTable(String table, String where, String[] args) {
        boolean flag = false;
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mFile, null);
        Cursor cursor = db.query(table, null, where, args, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            flag = true;
        }
        cursor.close();
        db.close();
        return flag;
    }

    public boolean insertItem(String table, ContentValues cv) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mFile, null);
        if (db.insert(table, null, cv) == 1) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }

    public boolean delteItem(String table, String where, String[] args) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mFile, null);
        int i = db.delete(table, where, args);
        db.close();
        if (i > 0) return true;
        return false;
    }

    public boolean updateItem(String table, String where, String[] args, ContentValues values) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(mFile, null);
        int i = db.update(table, values, where, args);
        db.close();
        if (i > 0) return true;
        return false;
    }

    public QueryResult query(String table, String where, String[] args) {
        QueryResult queryResult = new QueryResult();
        queryResult.db = SQLiteDatabase.openOrCreateDatabase(mFile, null);
        queryResult.cursor = queryResult.db.query(table, null, where, args, null, null, null);
        return queryResult;
    }

    public class QueryResult {
        public SQLiteDatabase db;
        public Cursor cursor;
    }
}
