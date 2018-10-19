package com.example.lixy.myapplication.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2017/12/2119:14
 */
public class SQLiteHelp extends SQLiteOpenHelper {

    private static final String DB_NAME = "student.db";
    private static final int DB_VERSION = 1;

    public SQLiteHelp(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + StudentTable.TABLE_NAME + "(id integer primary key autoincrement," +
                StudentTable.ROW_NAME + " varchar(20)," +
                StudentTable.ROW_AGE + " intager default 18," +
                StudentTable.ROW_SEX + " varchar(10) default '男')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > 1){
            db.execSQL("drop table if exists " + StudentTable.TABLE_NAME);
        }
        onCreate(db);
    }

    public Cursor getStudent(){
        Cursor cursor = getReadableDatabase().rawQuery("select * from student ",null);
        return cursor;
    }
}
