package com.seatrend.vendor.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.seatrend.vendor.IInspect;
import com.seatrend.vendor.ServiceLisener;


/**
 * Created by ly on 2020/4/22 9:50
 */
public class AIDLService extends Service {

    private String name;

    private RemoteCallbackList<ServiceLisener> mListener = new RemoteCallbackList<>();

    private Binder binder = new IInspect.Stub() {
        @Override
        public void setName(String s) {
            name = s;
        }

        @Override
        public String getName() {
            //请求是耗时的
//            try {
//                Thread.sleep(5000);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            return name;
        }

        @Override
        public void sendUserInfo(String user, String psw, ServiceLisener servicelisener) {
            try {
//                mListener.register(servicelisener);
//                int n=mListener.beginBroadcast();
//                showLog("服务数目 = "+n);
                servicelisener.currentState(200); //返回当前状态
                Thread.sleep(1500);  //休息1.5s
                servicelisener.serviceSuccess("成功 user : " + user + " psw : " + psw);
                Thread.sleep(1500);  //休息1.5s
                servicelisener.serviceSuccess("返回失败!!!");
            } catch (Exception e) {
                e.printStackTrace();
                showLog("服务sendUserInfo异常 ");
            }
        }

        @Override
        public void sendDataToServer(String sendJsonData, ServiceLisener servicelisener) {

        }

        @Override
        public void getResultByServer(String sendJsonData, ServiceLisener servicelisener) {

        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        showLog("binder OK ");
        return binder;
    }

    public void showLog(Object msg) {
        Log.d("lylog", "------" + msg + "");
    }


    //服务器向client 发送数据
    public void sendMsgToClient(String strS) {
        int n = mListener.beginBroadcast();
        showLog("服务数目 = " + n);
        for (int i = 0; i < n; i++) {
            ServiceLisener l = mListener.getBroadcastItem(i);
            if (l != null) {
//                try {
//                    showLog("服务送数据开始");
//                    l.callback(strS);//服务端通过这个向客户端发送消息
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
            }
        }
        mListener.finishBroadcast();
    }
}
