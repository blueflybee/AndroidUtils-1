package com.cjs.utils;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/1/14 20:48.
 */
public class StringUtils {
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0) && isBlank(str);
    }
}
