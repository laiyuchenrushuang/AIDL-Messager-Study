package com.seatrend.vendor;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.seatrend.vendor.service.AIDLService;
import com.seatrend.vendor.service.MessengerService;

public class MainActivity extends AppCompatActivity {

    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
    }

    public void doClick(View view) {
        AIDLService service = new AIDLService();
        service.sendMsgToClient(text.getText().toString());
    }
}
