package com.example.lixy.myapplication.sqlite;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixy.myapplication.MyApp;
import com.example.lixy.myapplication.R;

import java.util.Random;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2017/12/2119:54
 */
public class SqlActivity extends AppCompatActivity implements View.OnClickListener{
    private final static String TAG = SqlActivity.class.getSimpleName();
    private Button btn_insert;
    private Button btn_query;
    private Button btn_update;
    private Button btn_delete;
    private TextView textView;
    private StudentOberser studentOberser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_query = (Button) findViewById(R.id.btn_query);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        textView = (TextView)findViewById(R.id.text);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        btn_insert.setOnClickListener(this);
        btn_query.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        btn_delete.setOnClickListener(this);

        studentOberser = new StudentOberser(new Handler());
        getContentResolver().registerContentObserver(Uri.parse("content://com.bcjm.demo.im/student_im"), true, studentOberser);
    }

    @Override
    public void onClick(View v) {
        ContentResolver contentResolver = getContentResolver();
        switch (v.getId()){
            case R.id.btn_insert:
                ContentValues contentValues = new ContentValues();
                contentValues.put(StudentTable.ROW_NAME, "张三" + new Random().nextInt(1000));
                contentValues.put(StudentTable.ROW_AGE, 16);
                contentValues.put(StudentTable.ROW_SEX, new Random().nextInt(1000) % 2 == 0 ? "男" : "女");
                Uri uri = contentResolver.insert(Uri.parse("content://com.bcjm.demo.im/student_im"),contentValues);
                break;

            case R.id.btn_query:
                //Cursor cursor = contentResolver.query(Uri.parse("content://com.bcjm.demo.im/student"),null,null,null,null);
                Cursor cursor = new SQLiteHelp(MyApp.getApplication()).getStudent();
                if (cursor != null){
                    StringBuffer stringBuffer = new StringBuffer();
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex(StudentTable.ROW_NAME));
                        String age = cursor.getString(cursor.getColumnIndex(StudentTable.ROW_AGE));
                        String sex = cursor.getString(cursor.getColumnIndex(StudentTable.ROW_SEX));
                        Log.e(TAG,"name:" + name + "  age:" + age + "  sex:" + sex);
                        stringBuffer.append("name:").append(name).append("  age:").append(age).append("  sex:").append(sex).append("\n");
                    }
                    textView.setText(stringBuffer.toString());
                }
                break;

            case R.id.btn_delete:
                String where = StudentTable.ROW_SEX + "=?";
                int row = contentResolver.delete(Uri.parse("content://com.bcjm.demo.im/student_im"),null,null);
                Toast.makeText(this, "deleted " + row, Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_update:
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put(StudentTable.ROW_NAME,"李四");
                String where1 = StudentTable.ROW_SEX + "=?";
                row = contentResolver.update(Uri.parse("content://com.bcjm.demo.im/student_im"),contentValues1,where1,new String[]{"男"});
                Toast.makeText(this, "update " + row, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class StudentOberser extends ContentObserver{

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public StudentOberser(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            Log.d(TAG,"onChange(boolean selfChange)  " + selfChange);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            Toast.makeText(SqlActivity.this, "uri:" + uri.toString() + "   selfChange:" + selfChange, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(studentOberser);
    }
}
