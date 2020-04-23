package com.seatrend.client_2;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.seatrend.vendor.IPerson;

public class MainActivity extends AppCompatActivity {

    private final int MSG_FROM_CLIENT = 0x01;
    private final int FROM_SERVER = 0x02;

    private IPerson iPerson;

    EditText edit;
    EditText edit_r;
    Button bt_send;
    Button bt_get;
    Button bn_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit = findViewById(R.id.text);
        edit_r = findViewById(R.id.r_text);
        bt_send = findViewById(R.id.send);
        bt_get = findViewById(R.id.get);
        bn_msg = findViewById(R.id.bn_msg);

        bindEvent();
    }

    private void bindEvent() {
        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindServiceByAidl();

                if (iPerson == null) {
                    showLog(" iPerson == null");
                    return;
                }

                String s = edit.getText().toString();
                try {
                    iPerson.setName(s);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        bt_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    edit_r.setText(iPerson.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        bn_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindServiceByMesseger();
                if (mBond) {
                    Message msg = Message.obtain();
                    msg.what = MSG_FROM_CLIENT;
                    msg.replyTo = clientMessenger;
//                    clientMessage.obj = edit.getText().toString();  //java.lang.RuntimeException: Can't marshal non-Parcelable objects across processes.
                    Bundle data = new Bundle();
                    data.putString("client_msg", edit.getText().toString());
                    msg.setData(data);
                    try {
                        serverMessenger.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    showLog("服务绑定失败");
                }
            }
        });
    }


    private void bindServiceByAidl() {
        Intent intent = new Intent("com.seatrend.vendor.respond_message");
        intent.setPackage("com.seatrend.vendor");
        bindService(intent, cnnec, BIND_AUTO_CREATE);
    }

    private void bindServiceByMesseger() {
        Intent intent = new Intent();
        intent.setAction("com.seatrend.vendor.message_service");
        intent.setPackage("com.seatrend.vendor");
        bindService(intent, msg_cnnec, BIND_AUTO_CREATE);
    }

    private ServiceConnection cnnec = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            showLog("onServiceConnected");
            iPerson = IPerson.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            showLog("onServiceDisconnected");
        }
    };


    private boolean mBond;  //flag
    private Messenger serverMessenger;  //第二种进程间通讯

    private ServiceConnection msg_cnnec = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //连接成功
            serverMessenger = new Messenger(service);
            showLog("Messenger 服务连接成功");
            mBond = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            showLog("Messenger 服务连接失败");
            mBond = false;
        }
    };


    @SuppressLint("HandlerLeak")
    private Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            if (msg.what == FROM_SERVER) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        edit_r.setText(msg.getData().getString("server_msg"));
                    }
                });
            }
        }
    });

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cnnec != null) {
            unbindService(cnnec);
        }
        if (msg_cnnec != null) {
            unbindService(msg_cnnec);
        }
    }

    public void showLog(String msg) {
        Log.d("lylog", "------" + msg + "");
    }
}
