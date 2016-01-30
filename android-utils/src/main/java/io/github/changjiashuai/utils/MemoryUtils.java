package io.github.changjiashuai.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;


/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/1/30 11:35.
 *
 *
 * Print memory info. such as:
 *
 * MemTotal:        1864292 kB
 * MemFree:          779064 kB
 * Buffers:            4540 kB
 * Cached:           185656 kB
 * SwapCached:        13160 kB
 * Active:           435588 kB
 * Inactive:         269312 kB
 * Active(anon):     386188 kB
 * Inactive(anon):   132576 kB
 * Active(file):      49400 kB
 * Inactive(file):   136736 kB
 * Unevictable:        2420 kB
 * Mlocked:               0 kB
 * HighTotal:       1437692 kB
 * HighFree:         520212 kB
 * LowTotal:         426600 kB
 * LowFree:          258852 kB
 * SwapTotal:        511996 kB
 * SwapFree:         171876 kB
 * Dirty:               412 kB
 * Writeback:              0kB
 * AnonPages:        511924 kB
 * Mapped:           152368 kB
 * Shmem:              1636 kB
 * Slab:             109224 kB
 * SReclaimable:      75932 kB
 * SUnreclaim:        33292 kB
 * KernelStack:       13056 kB
 * PageTables:        28032 kB
 * NFS_Unstable:          0 kB
 * Bounce:                0 kB
 * WritebackTmp:          0 kB
 * CommitLimit:     1444140 kB
 * Committed_AS:   25977748 kB
 * VmallocTotal:     458752 kB
 * VmallocUsed:      123448 kB
 * VmallocChunk:     205828 kB
 *
 */
public class MemoryUtils {


    /**
     * Get memory info of device.
     * @param context
     * @return
     */
    public static ActivityManager.MemoryInfo getMemoryInfo(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }

    /**
     * 获取android当前可用内存大小
     */
    public static String getAvailMemory(Context context) {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return Formatter.formatFileSize(context, memoryInfo.availMem);
    }
}
