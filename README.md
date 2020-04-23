# AIDL-Messager-Study

1.保证Server 和 Client  aidl的文件包名一致，并不是两个apk的包名一致【卡了半天】



2.启动形式  又卡了半天

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
    
    
3. server的service 要注册，没得权限申请


        <service android:name=".service.AIDLService">
            <intent-filter>
                <action android:name="com.seatrend.vendor.respond_message" />
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>

        </service>

        <service android:name=".service.MessengerService">
            <intent-filter>
                <action android:name="com.seatrend.vendor.message_service" />
            </intent-filter>
        </service>
        
        
4. Messeger 数据传递用bundle,序列化问题


    
