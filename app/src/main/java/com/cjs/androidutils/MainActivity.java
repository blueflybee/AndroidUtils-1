package com.cjs.androidutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cjs.utils.ManifestUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TAG", "versionName:" + ManifestUtils.getVersionName(this));
        Log.i("TAG", "versionCode:" + ManifestUtils.getVersionCode(this));
        Log.i("TAG", "channel:" + ManifestUtils.getChannel(this, "abc"));
    }
}
