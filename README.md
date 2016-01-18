# AndroidUtils
>Android Common Utils

### Repository

>Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

``` Gradle
allprojects {
	repositories {
		...
		 maven {
                url "http://dl.bintray.com/changjiashuai/maven" 
         }
	}
}
```

### Dependency

>Add this to your module's `build.gradle` file:

```Gradle
dependencies {
    compile fileTree(include: ['*.jar'], dir: 'libs')
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:23.1.1'
    ...
    compile 'changjiashuai.utils:android-utils:0.0.3'
    ...
}
```

###Summary

####ActivityManager

```Java
* addActivity(Activity activity) //添加Activity到堆栈
* currentActivity() //获取当前Activity（堆栈中最后一个压入的
* finishActivity() //结束当前Activity（堆栈中最后一个压入的）
* finishActivity(Activity activity) //结束指定的Activity
* finishActivity(Class<?> cls) //结束指定类名的Activity
* finishAllActivity() //结束所有Activity
* Activity getActivity(String activityName) //根据ActivityName获取堆中Activity实例
* appExit(Context context) //退出应用程序
```

####ApkUtils

```Java
* String getChannelFromApk(Context context, String channelKey) //从apk中获取Meta-Data信息
* boolean isAppInstalled(Context context, String packageName) //判断某个应用是否已经安装
* uninstall(Context context, String packageName) //卸载一个app
* install(Context context, File uriFile) //安装一个apk文件
```

####BitmapUtils

```Java
* Drawable bitmapToDrawable(Bitmap bitmap) //Bitmap转换为Drawable
* Bitmap drawableToBitmap(Drawable drawable) //Drawable转换为Bitmap
* String bitmapToString(Bitmap bitmap) //Bitmap转换为Base64 String
* Bitmap byteToBitmap(byte[] bytes) //byte[]转换为Bitmap
* byte[] bitmapToByte(Bitmap bitmap) //Bitmap转换为byte[]
* Bitmap scaleImage(Bitmap originBitmap, float scaleWidth, float scaleHeight) //
* ...
```

####DeviceUtils

```Java
* boolean isPhone(Context context) //判断当前设备是否为手机
* sendSms(Context context, String phoneNumber, String content) //发送短信
* callDial(Context context, String phoneNumber) //跳转至拨号界面
* callPhone(Context context, String phoneNumber) //拨打电话 *request android.permission.CALL_PHONE* self check permission
* int getPhoneType(Context context) //返回移动终端类型 
    1. PHONE_TYPE_NONE :0 手机制式未知 
    2. PHONE_TYPE_GSM  :1 手机制式为GSM，移动和联通 
    3. PHONE_TYPE_CDMA :2
    4. 手机制式为CDMA，电信 PHONE_TYPE_SIP:3
* goHome(Context context) //主动回到Home，后台运行
* boolean isActiveSoftInput(Context context) //判断输入法是否处于激活状态
* showInputSoftFromWindowMethod(Context context, View view) //显示键盘
* hideInputSoftFromWindowMethod(Context context, View view) //隐藏键盘
* int getTopBarHeight(Activity activity) //获取状态栏高度＋标题栏(ActionBar)高度 (注意，如果没有ActionBar，那么获取的高度将和上面的是一样的，只有状态栏的高度)
* int getNavigationBarHeight(Context context) //获取navigationbar高度
* int getStatusBarHeight(Context context) //获取statusbar高度
* boolean isSoftKeyAvailable(Activity activity) //判断是否有软控制键（手机底部几个按钮）
* List<String> getAppPackageNameList(Context context) //获取非系统应用包名
* copyToClipBoard(Context context, String content) //复制到剪切板
* DisplayMetrics getScreenPix(Activity activity) //获取手机大小（分辨率）
* vibrate(Context context, long duration) //震动 request android.Manifest.permission#VIBRATE
* String getUUID(Context context) //获取UUID
* String getMac(Context context) //获取MAC地址 *request android.permission.ACCESS_WIFI_STATE*
* String getIMEI(Context context) //获取IMEI *request android.permission.READ_PHONE_STATE*
* boolean isProcessRunning(Context context, String processName) //进程是否运行
* boolean isServiceRunning(Context context, String className) //服务是否运行
* String getLocalIPAddress() //获取本机IP地址
```

####ExternalStorageUtils


