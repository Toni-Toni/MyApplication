package com.example.lixy.myapplication.sqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2017/12/2119:28
 */
public class StudentContentProvider extends ContentProvider {
    private SQLiteDatabase database;
    private UriMatcher uriMatcher;
    private static final String AUTHORITY = "com.bcjm.demo.im";
    private static final String STUDENT_PATH = "student_im";
    @Override
    public boolean onCreate() {
        SQLiteHelp sqLiteHelp = new SQLiteHelp(getContext());
        database = sqLiteHelp.getWritableDatabase();
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, STUDENT_PATH,0);
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        int code = uriMatcher.match(uri);
        switch (code){
            case 0:
                cursor = database.query(StudentTable.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case 1:
                cursor = database.rawQuery("select * from student ",null);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = uriMatcher.match(uri);
        long row = 0;
        switch (code){
            case 0:
                row = database.insert(StudentTable.TABLE_NAME,null,values);
                break;
        }
        Uri uri1 = ContentUris.withAppendedId(uri,row);
        getContext().getContentResolver().notifyChange(uri1,null);
        return uri1;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        int row = 0;
        switch (code){
            case 0:
                row = database.delete(StudentTable.TABLE_NAME,selection,selectionArgs);
                break;
        }
        return row;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = uriMatcher.match(uri);
        int row = 0;
        switch (code){
            case 0:
                row = database.update(StudentTable.TABLE_NAME,values,selection,selectionArgs);
                break;
        }
        Uri uri1 = ContentUris.withAppendedId(uri,row);
        getContext().getContentResolver().notifyChange(uri1,null);
        return row;
    }
}
