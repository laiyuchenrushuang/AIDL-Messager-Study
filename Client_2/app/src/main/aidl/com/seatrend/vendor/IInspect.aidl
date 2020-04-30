package com.seatrend.vendor;

// Declare any non-default types here with import statements
import com.seatrend.vendor.ServiceLisener;

interface IInspect {
  void  setName(String s);
  String  getName();
  void sendUserInfo(String user,String psw,ServiceLisener servicelisener);
  void sendDataToServer(String sendJsonData,ServiceLisener servicelisener);
  void getResultByServer(String sendJsonData,ServiceLisener servicelisener);
}
