// IMyAidlInterface.aidl
package com.example.lixy.myapplication;

// Declare any non-default types here with import statements

import com.example.lixy.myapplication.bean.Person;
import com.example.lixy.myapplication.IRemoteServiceCallback;

interface IMyAidlInterface {
    List<Person> getPersons();

    void addCallback(IRemoteServiceCallback callback);
    void removeCallbak(IRemoteServiceCallback callback);

    void addPersonIn(in Person person);
    void addPersonOut(out Person person);
    void addPersonInOut(inout Person person);
    void setPersonName(in Person person,String name);
    void setPersonAge(in Person person,int age);
}
