package com.example.lixy.myapplication.activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.lixy.myapplication.IMyAidlInterface;
import com.example.lixy.myapplication.IRemoteServiceCallback;
import com.example.lixy.myapplication.bean.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * Copyright©2018 ihalma All Rights Reserved.
 * @Description:  aidl服务端
 * @author: li.xy
 * @date: 2018/9/2720:02
 */
public class AIDLService extends Service {
    private RemoteCallbackList<IRemoteServiceCallback> remoteCallbackList = new RemoteCallbackList<IRemoteServiceCallback>();
    private ArrayList<Person> mPersons;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        mPersons = new ArrayList<>();
        return myAidlInterface;
    }

    private IBinder myAidlInterface = new IMyAidlInterface.Stub() {
        @Override
        public List<Person> getPersons() throws RemoteException {

            return mPersons;
        }

        @Override
        public void addCallback(IRemoteServiceCallback callback) throws RemoteException {
            remoteCallbackList.register(callback);
        }

        @Override
        public void removeCallbak(IRemoteServiceCallback callback) throws RemoteException {
            remoteCallbackList.unregister(callback);
        }

        @Override
        public void addPersonIn(Person person) throws RemoteException {
            mPersons.add(person);
        }

        @Override
        public void addPersonOut(Person person) throws RemoteException {
            mPersons.add(person);
        }

        @Override
        public void addPersonInOut(Person person) throws RemoteException {
            mPersons.add(person);
            int n = remoteCallbackList.beginBroadcast();
            for (int i=0; i < n;i++){
                IRemoteServiceCallback listener = remoteCallbackList.getBroadcastItem(i);
                if (listener != null){
                    listener.personChanged(person);
                }

            }
            remoteCallbackList.finishBroadcast();
        }

        @Override
        public void setPersonName(Person person, String name) throws RemoteException {

        }

        @Override
        public void setPersonAge(Person person, int age) throws RemoteException {

        }
    };
}
