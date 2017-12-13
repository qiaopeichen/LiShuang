package com.example.qiaopc.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by qiaopc on 2017/12/13 0013.
 */

public class PersonOpenHelper extends SQLiteOpenHelper{
    public PersonOpenHelper(Context context) {
        super(context, "person.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库中表的方法
        db.execSQL("create table person " +
                "(_id integer primary key autoincrement, name varchar(20), phone varchar(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
