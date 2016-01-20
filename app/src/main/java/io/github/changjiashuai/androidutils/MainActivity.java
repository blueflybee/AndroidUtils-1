package io.github.changjiashuai.androidutils;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.github.changjiashuai.utils.ApkUtils;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TAG", "getChannelFromApk:" + ApkUtils.getChannelFromApk(this, "versionCode"));
    }
}
