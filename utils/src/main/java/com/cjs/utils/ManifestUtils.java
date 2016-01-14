package com.cjs.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/1/14 16:50.
 */
public class ManifestUtils {

    /**
     * @return versionCode [e.g.: 1]
     */
    public static int getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null ? packageInfo.versionCode : 0;
    }

    /**
     * @return versionName [e.g.: 1.0]
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null ? packageInfo.versionName : null;
    }

    /**
     * @return AndroidManifest Meta Data
     */
    public static String getMetaData(Context context, String metaKey) {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return applicationInfo != null ? (applicationInfo.metaData != null ? applicationInfo.metaData.getString(metaKey) : null) : null;
    }

    /**
     * @return channel value
     */
    public static String getChannel(Context context, String channelKey) {
        return getMetaData(context, channelKey);
    }
}
