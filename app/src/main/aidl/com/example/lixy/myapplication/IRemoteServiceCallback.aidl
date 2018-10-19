// IRemoteServiceCallback.aidl
package com.example.lixy.myapplication;

// Declare any non-default types here with import statements
import com.example.lixy.myapplication.bean.Person;

interface IRemoteServiceCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */

    void personChanged(inout Person person);
}
