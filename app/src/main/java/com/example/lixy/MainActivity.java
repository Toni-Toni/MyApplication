package com.example.lixy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lixy.myapplication.R;
import com.example.lixy.myapplication.activity.AIDLClientActivity;
import com.example.lixy.myapplication.activity.BaseActivity;
import com.example.lixy.myapplication.activity.FragmentDemoActivity;
import com.example.lixy.myapplication.activity.HandlerDemoActivity;
import com.example.lixy.myapplication.activity.IntentServiceActivity;
import com.example.lixy.myapplication.activity.ViewDemoActivity;
import com.example.lixy.myapplication.databinding.DatabindingActivity;
import com.example.lixy.myapplication.notification.NotificationActivity;
import com.example.lixy.myapplication.sqlite.SqlActivity;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/2/2717:23
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_fragment,btn_intentservice,btn_handler,btn_notification,btn_sql,btn_aidl,
        btn_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btn_intentservice = findViewById(R.id.btn_intentservice);
        btn_handler = findViewById(R.id.btn_handler);
        btn_notification = findViewById(R.id.btn_notification);
        btn_sql = findViewById(R.id.btn_sql);
        btn_fragment = findViewById(R.id.btn_fragment);
        btn_view = findViewById(R.id.btn_view);
        btn_aidl = findViewById(R.id.btn_aidl);

        btn_handler.setOnClickListener(this);
        btn_notification.setOnClickListener(this);
        btn_sql.setOnClickListener(this);
        btn_intentservice.setOnClickListener(this);
        btn_fragment.setOnClickListener(this);
        btn_view.setOnClickListener(this);
        btn_aidl.setOnClickListener(this);
        findViewById(R.id.btn_databinding).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_intentservice:
                startActivity(new Intent(this, IntentServiceActivity.class));
                break;
            case R.id.btn_handler:
                startActivity(new Intent(this, HandlerDemoActivity.class));
                break;
            case R.id.btn_notification:
                startActivity(new Intent(this, NotificationActivity.class));
                break;
            case R.id.btn_sql:
                startActivity(new Intent(this, SqlActivity.class));
                break;
            case R.id.btn_fragment:
                startActivity(new Intent(this, FragmentDemoActivity.class));
                break;
            case R.id.btn_view:
                startActivity(new Intent(this, ViewDemoActivity.class));
                break;
            case R.id.btn_aidl:
                startActivity(new Intent(this, AIDLClientActivity.class));
                break;
            case R.id.btn_databinding:
                startActivity(new Intent(this, DatabindingActivity.class));
                /*Intent intent = new Intent(getApplication().getPackageName() + ".Data");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setData(Uri.parse("xxx://" + getApplication().getPackageName() + ":123" + "/user"));
                startActivity(intent);*/
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: isFinishing " + isFinishing());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
