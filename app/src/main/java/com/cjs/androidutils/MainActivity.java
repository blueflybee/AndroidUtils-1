package com.cjs.androidutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cjs.utils.DeviceUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TAG", "getLocalIPAddress:" + DeviceUtils.getLocalIPAddress());
        Log.i("TAG", "getIMEI:" + DeviceUtils.getIMEI(this));
        Log.i("TAG", "getMac:" + DeviceUtils.getMac(this));
        Log.i("TAG", "getUDID:" + DeviceUtils.getUDID(this));
    }
}
