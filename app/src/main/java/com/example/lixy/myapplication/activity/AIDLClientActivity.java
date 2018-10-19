package com.example.lixy.myapplication.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lixy.myapplication.IMyAidlInterface;
import com.example.lixy.myapplication.IRemoteServiceCallback;
import com.example.lixy.myapplication.R;
import com.example.lixy.myapplication.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 *          Copyright©2015 ihalma All Rights Reserved.
 * @Description:
 * @author: li.xy
 * @date: 2018/3/1215:27
 */
public class AIDLClientActivity extends BaseActivity implements View.OnClickListener{
    private Button btn_add,btn_get,btn_update;
    private TextView tv_content;
    private IMyAidlInterface manager;
    private boolean isBineded = false;
    private List<Person> personList = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);

        tv_content = findViewById(R.id.tv_content);
        btn_add = findViewById(R.id.btn_add);
        btn_get = findViewById(R.id.btn_get);
        btn_update = findViewById(R.id.btn_update);

        btn_add.setOnClickListener(this);
        btn_get.setOnClickListener(this);
        btn_update.setOnClickListener(this);

        bindService();
    }

    private void addPerson(){
        if (!isBineded){
            bindService();
            showToast("当前与服务端处于未连接状态，正在尝试重连，请稍后再试");
            return;
        }
        if (manager == null) return;
         Person person = new Person();
         person.setName("tony" + personList.size());
         person.setAge(17);
        try {
            Log.d(TAG, "addPerson1: " + person.toString());
            manager.addPersonInOut(person);
            Log.d(TAG, "addPerson2: " + person.toString());
        } catch (RemoteException e) {

        }
    }

    private void updatePerson(){
        if (!isBineded){
            bindService();
            showToast("当前与服务端处于未连接状态，正在尝试重连，请稍后再试");
            return;
        }
        if (manager == null && personList.size() == 0) return;
        Person person = personList.get(0);
        try {
            Log.d(TAG, "updatePerson: " + person.toString());
            manager.setPersonName(person,"updatedName");
        } catch (RemoteException e) {

        }
    }

    private void getPerson(){
        if (!isBineded){
            bindService();
            showToast("当前与服务端处于未连接状态，正在尝试重连，请稍后再试");
            return;
        }
        if (manager == null && personList.size() == 0) return;
        try {
            personList = manager.getPersons();
            tv_content.setText("");
            for (Person person:
                 personList) {
                tv_content.append(person.toString() + "\n");
            }
            Log.d(TAG, "getPerson: " + tv_content.getText().toString());
        } catch (RemoteException e) {

        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (!isBineded){
            bindService();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isBineded){
            try {
                manager.removeCallbak(remoteServiceCallback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            unbindService(serviceConnection);
        }
    }

    private void bindService(){
        Intent intent = new Intent("com.example.lixy.aidl");
        intent.setPackage("com.example.lixy.myapplication");
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected: ");
            isBineded = true;
            manager = IMyAidlInterface.Stub.asInterface(service);
            if (manager != null){
                try {
                    manager.addCallback(remoteServiceCallback);
                    personList = manager.getPersons();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
            isBineded = false;
        }
    };

    private IRemoteServiceCallback remoteServiceCallback = new IRemoteServiceCallback.Stub() {
        @Override
        public void personChanged(Person person) throws RemoteException {
            showToast(person.toString());
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                addPerson();
                break;
            case R.id.btn_update:
                updatePerson();
                break;
            case R.id.btn_get:
                getPerson();
                break;
        }
    }
}
