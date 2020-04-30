// ServiceLisener.aidl
package com.seatrend.vendor;

// Declare any non-default types here with import statements

interface ServiceLisener {
      void currentState(int state);
      void serviceError(String strError);
      void serviceSuccess(String strSuccess);
}
