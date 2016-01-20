package io.github.changjiashuai.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/1/12 12:03.
 *
 * request: android.permission.WRITE_EXTERNAL_STORAGE
 */
public class StorageUtils {

    private static final String INDIVIDUAL_DIR_NAME = "cache";

    /**
     * @return application cache directory. Cache directory will be created on SD card
     * "/Android/data/[app_package_name]/cache" [e.g.:/storage/emulated/0/Android/data/com.cjs.androidutils/cache]
     */
    public static File getCacheDirectory(Context context) {
        return getCacheDirectory(context, true);
    }

    public static File getCacheDirectory(Context context, boolean external) {
        File appCacheDir = null;
        String externalStorageState = Environment.getExternalStorageState();
        if (external && Environment.MEDIA_MOUNTED.equals(externalStorageState)) {
            appCacheDir = getExternalCacheDir(context);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }

    public static File getExternalCacheDir(Context context) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                return null;
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return appCacheDir;
    }

    /**
     * @return individual application cache directory
     */
    public static File getIndividualCacheDirectory(Context context) {
        return getIndividualCacheDirectory(context, INDIVIDUAL_DIR_NAME);
    }

    /**
     * @param cacheDir "image"
     * @return [e.g.:/storage/emulated/0/Android/data/com.cjs.androidutils/cache/image]
     */
    public static File getIndividualCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        appCacheDir = getCacheDirectory(context);
        File individualCacheDir = new File(appCacheDir, cacheDir);
        if (!individualCacheDir.exists()) {
            if (!individualCacheDir.mkdir()) {
                individualCacheDir = appCacheDir;
            }
        }
        return individualCacheDir;
    }

    /**
     * @return specified application cache directory.
     */
    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        return getOwnCacheDirectory(context, cacheDir, true);
    }

    /**
     * @param cacheDir "AppName"--- It seems not work??
     * @return [e.g:/data/data/com.cjs.androidutils/cache]
     */
    public static File getOwnCacheDirectory(Context context, String cacheDir, boolean external) {
        File appCacheDir = null;
        if (external && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            appCacheDir = new File(Environment.getExternalStorageDirectory(), cacheDir);
        }
        if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    /**
     * 判断SDCard是否可用
     */
    public static boolean existSDCard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取可用空间大小
     */
    public static long getAvailaleSize() {
        if (!existSDCard()) {
            return 0l;
        }
        File path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    /**
     * 获取SD大小
     */
    public static long getAllSize() {
        if (!existSDCard()) {
            return 0l;
        }
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getBlockCount();
        return availableBlocks * blockSize;
    }

    /**
     * 多个SD卡时 取外置SD卡
     */
    public static String getExternalStorageDirectory() {
        // 参考文章
        // http://blog.csdn.net/bbmiku/article/details/7937745
        Map<String, String> map = System.getenv();
        Log.i("TAG", "map=" + map);
        String[] values = new String[map.values().size()];
        map.values().toArray(values);
        String path = values[values.length - 1];
        if (path.startsWith("/mnt/") && !Environment.getExternalStorageDirectory()
                .getAbsolutePath()
                .equals(path)) {
            return path;
        } else {
            return null;
        }
    }
}
