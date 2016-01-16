package com.cjs.androidutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cjs.utils.DeviceUtils;
import com.cjs.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TAG", "isPhone:" + DeviceUtils.isPhone(this));
        Log.i("TAG", "getPhoneType:" + DeviceUtils.getPhoneType(this));
        Log.i("TAG", "isActiveSoftInput:" + DeviceUtils.isActiveSoftInput(this));
        Log.i("TAG", "getTopBarHeight:" + DeviceUtils.getTopBarHeight(this));
        Log.i("TAG", "getNavigationBarHeight:" + DeviceUtils.getNavigationBarHeight(this));
        Log.i("TAG", "getStatusBarHeight:" + DeviceUtils.getStatusBarHeight(this));
        Log.i("TAG", "isSoftKeyAvailable:" + DeviceUtils.isSoftKeyAvailable(this));
        Log.i("TAG", "getAppPackageNameList:" + DeviceUtils.getAppPackageNameList(this));
        Log.i("TAG", "getScreenPix:" + DeviceUtils.getScreenPix(this));
        Log.i("TAG", "getNetworkType:" + NetworkUtils.getNetworkType(this));
        Log.i("TAG", "vibrate:");
        DeviceUtils.vibrate(this, 1000);
    }
}
