package com.cjs.androidutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.cjs.utils.ExternalStorageUtils;
import com.cjs.utils.StorageUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String sdCardPath = ExternalStorageUtils.getSdCardPath();
        Log.i("TAG", "sdCardPath=" + sdCardPath);
        Map<String, File> map = ExternalStorageUtils.getAllStorageLocations();
        Log.i("TAG", "map=" + map);
        for (Map.Entry<String, File> entry : map.entrySet()) {
            String key = entry.getKey();
            File value = entry.getValue();
            Log.i("TAG", "[" + key + "," + value.getName() + "]");
        }

        Log.i("TAG", "=======================");
        try {
            Log.i("TAG", "cacheDir=" + StorageUtils.getCacheDirectory(this));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Log.i("TAG", "individual cacheDir=" + StorageUtils.getIndividualCacheDirectory(this, "image"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("TAG", "own cacheDir=" + StorageUtils.getOwnCacheDirectory(this, "AppName", false));
    }
}
