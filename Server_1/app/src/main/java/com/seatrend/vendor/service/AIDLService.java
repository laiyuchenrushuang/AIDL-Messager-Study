package com.seatrend.vendor.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.seatrend.vendor.IPerson;


/**
 * Created by ly on 2020/4/22 9:50
 */
public class AIDLService extends Service {

    private String name;

    private Binder binder = new IPerson.Stub() {
        @Override
        public void setName(String s) throws RemoteException {
            name = s;
        }

        @Override
        public String getName() throws RemoteException {
            return name;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        showLog("binder OK ");
        return binder;
    }

    public void showLog(String msg) {
        Log.d("lylog", "------" + msg + "");
    }
}
