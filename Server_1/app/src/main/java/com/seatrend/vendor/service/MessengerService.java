package com.seatrend.vendor.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ly on 2020/4/22 15:42
 */
public class MessengerService extends Service {

    private final int MSG_FROM_CLIENT = 0x01;
    private final int FROM_SERVER = 0x02;
    @SuppressLint("HandlerLeak")
    private Messenger mMessenger = new Messenger(new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    showLog("CLIENT");

                    Message m = Message.obtain();
                    Messenger clientMessenger = msg.replyTo;
                    String clientMsg = msg.getData().getString("client_msg");
                    Bundle data = new Bundle();
                    data.putString("server_msg","来自服务器的消息,收到：" + clientMsg);
                    m.what = FROM_SERVER;
                    m.setData(data);

                    try {
                        clientMessenger.send(m);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                default:
                    break;
            }

        }
    });


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }


    public void showLog(String msg) {
        Log.e("lylog", "-----------" + msg + "-----------");
    }
}
