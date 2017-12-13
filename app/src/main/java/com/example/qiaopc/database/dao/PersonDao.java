package com.example.qiaopc.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qiaopc.database.PersonOpenHelper;
import com.example.qiaopc.database.domain.PersonInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qiaopc on 2017/12/13 0013.
 */

public class PersonDao {

    private PersonOpenHelper personOpenHelper;

    private PersonDao(Context context) {
        personOpenHelper = new PersonOpenHelper(context);
    }

    private static PersonDao personDao = null;

    public static PersonDao getInstance(Context context) {
        if (personDao == null) {
            personDao = new PersonDao(context);
        }
        return personDao;
    }

    public void insert(String name, String phone) {
        SQLiteDatabase db = personOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone", phone);
        db.insert("person", null, values);
        db.close();
    }

    public void delete(String name) {
        SQLiteDatabase db = personOpenHelper.getWritableDatabase();
        db.delete("person", "name = ?", new String[]{name});
        db.close();
    }

    public void update(String name, String phone) {
        SQLiteDatabase db = personOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        db.update("person", values, "name = ?", new String[]{name});
        db.close();
    }

    public List<PersonInfo> findAll() {
        SQLiteDatabase db = personOpenHelper.getWritableDatabase();
        Cursor cursor = db.query("person", new String[]{"name", "phone"}, null, null, null, null, "_id desc");
        List<PersonInfo> personInfoList = new ArrayList<>();
        while (cursor.moveToNext()) {
            PersonInfo personInfo = new PersonInfo();
            personInfo.name = cursor.getString(0);
            personInfo.phone = cursor.getString(1);
            personInfoList.add(personInfo);
        }
        cursor.close();
        db.close();

        return personInfoList;
    }
}
