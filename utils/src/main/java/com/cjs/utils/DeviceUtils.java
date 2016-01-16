package com.cjs.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/1/14 18:53.
 */
public class DeviceUtils {

    /**
     * 判断当前设备是否为手机
     */
    public static boolean isPhone(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getPhoneType() != TelephonyManager.PHONE_TYPE_NONE;
    }

    /**
     * 发送短信
     */
    public static void sendSms(Context context, String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
        context.startActivity(intent);
    }

    /**
     * 跳转至拨号界面
     *
     * @param phoneNumber 电话号码
     */
    public static void callDial(Context context, String phoneNumber) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber 电话号码
     *
     *                    request android.permission.CALL_PHONE
     */
    public static void callPhone(Context context, String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
    }

    /**
     * 返回移动终端类型 PHONE_TYPE_NONE :0 手机制式未知 PHONE_TYPE_GSM  :1 手机制式为GSM，移动和联通 PHONE_TYPE_CDMA :2
     * 手机制式为CDMA，电信 PHONE_TYPE_SIP:3
     */
    public static int getPhoneType(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getPhoneType();
    }

    /**
     * 主动回到Home，后台运行
     */
    public static void goHome(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        context.startActivity(intent);
    }

    /**
     * 判断输入法是否处于激活状态
     */
    public static boolean isActiveSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isActive();
    }

    /**
     * 显示输入法
     */
    public static void showInputSoftFromWindowMethod(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    /**
     * 隐藏键盘
     */
    public static void hideInputSoftFromWindowMethod(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
    }

    /**
     * 获取状态栏高度＋标题栏(ActionBar)高度 (注意，如果没有ActionBar，那么获取的高度将和上面的是一样的，只有状态栏的高度)
     */
    public static int getTopBarHeight(Activity activity) {
        return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
    }

    /**
     * 获取navigationbar高度
     */
    public static int getNavigationBarHeight(Context context) {
        int height = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = resources.getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 获取statusbar高度
     */
    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 判断是否有软控制键（手机底部几个按钮）
     */
    public static boolean isSoftKeyAvailable(Activity activity) {
        final boolean[] isSoftKey = {false};
        final View activityRootView = (activity).getWindow().getDecorView().findViewById(android.R.id.content);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int rootViewHeight = activityRootView.getRootView().getHeight();
                int viewHeight = activityRootView.getHeight();
                int heightDiff = rootViewHeight - viewHeight;
                if (heightDiff > 100) {
                    isSoftKey[0] = true;
                }
            }
        });
        return isSoftKey[0];
    }

    /**
     * 获取非系统应用包名
     */
    public static List<String> getAppPackageNameList(Context context) {
        List<String> packageNames = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            String packageName = packageInfo.packageName;
            packageNames.add(packageName);
        }
        return packageNames;
    }

    /**
     * 复制到剪切板
     */
    public static void copyToClipBoard(Context context, String content) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("label", content);
            clipboardManager.setPrimaryClip(clipData);
        } else {
            android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setText(content);
        }
    }

    /**
     * 获取手机大小（分辨率）
     */
    public static DisplayMetrics getScreenPix(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    /**
     * 震动
     *
     * @param duration request android.Manifest.permission#VIBRATE
     */
    public static void vibrate(Context context, long duration) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, duration};
        vibrator.vibrate(pattern, -1);
    }

    /**
     * 获取UUID
     */
    public static String getUUID(Context context) {
        String uuid = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (StringUtils.isEmpty(uuid) || uuid.equals("9774d56d682e549c")
                || uuid.length() < 15) {
            SecureRandom random = new SecureRandom();
            uuid = new BigInteger(64, random).toString(16);
        }
        if (StringUtils.isEmpty(uuid)) {
            uuid = "";
        }
        return uuid;
    }

    /**
     * 获取MAC地址
     *
     * @return request android.permission.ACCESS_WIFI_STATE
     */
    public static String getMac(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo.getMacAddress();
    }

    /**
     * 获取IMEI request android.permission.READ_PHONE_STATE
     */
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    /**
     * 进程是否运行
     */
    public static boolean isProcessRunning(Context context, String processName) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessInfos) {
            if (runningAppProcessInfo.processName.equals(processName)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 服务是否运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (runningServiceInfos.size() == 0) {
            return false;
        }
        for (int i = 0; i < runningServiceInfos.size(); i++) {
            if (runningServiceInfos.get(i).service.getClassName().equals(className)) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 获取本机IP地址
     */
    public static String getLocalIPAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            return "0.0.0.0";
        }
        return "0.0.0.0";
    }
}
