package com.cjs.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/1/16 17:46.
 */
public class BitmapUtils {

    /**
     * 等比压缩
     */
    public static Bitmap compressBitmap(Bitmap bitmap, boolean needRecycle, int targetWidth, int targetHeight) {
        int sourceWidth = bitmap.getWidth();
        int sourceHeight = bitmap.getHeight();
        float scaleWidth = (float) targetWidth / sourceWidth;
        float scaleHeight = (float) targetHeight / sourceHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, sourceWidth, sourceHeight, matrix, true);
        if (needRecycle) {
            bitmap.recycle();
        }
        bitmap = bm;
        return bitmap;
    }

    /**
     * compress Bitmap to target size (Quality Compress)
     */
    public static Bitmap compressBitmap(Bitmap bitmap, boolean needRecycle, long maxSize) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        int options = 100;
        while (byteArrayOutputStream.toByteArray().length > maxSize) {
            byteArrayOutputStream.reset();//clear byteArrayOutputStream
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, byteArrayOutputStream);
            options -= 10;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        Bitmap newBitmap = BitmapFactory.decodeStream(byteArrayInputStream, null, null);
        if (needRecycle) {
            bitmap.recycle();
        }
        bitmap = newBitmap;
        return bitmap;
    }

    /**
     * save Bitmap to File
     */
    public static void saveBitmapToFile(Bitmap bitmap, File targetFile) {
        saveBitmapToFile(bitmap, targetFile, 100);
    }

    /**
     * save Bitmap to File
     *
     * @param quality 0..100
     */
    public static void saveBitmapToFile(Bitmap bitmap, File targetFile, int quality) {
        if (targetFile.exists()) {
            targetFile.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * create Bitmap Thumbnail
     *
     * @return Bitmap Thumbnail
     */
    public static Bitmap createBitmapThumbnail(Bitmap bitmap, boolean needRecycle, int newWidth, int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // compute scale factor
        float scaleWidth = (float) newWidth / width;
        float scaleHeight = (float) newHeight / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        if (needRecycle) {
            bitmap.recycle();
        }
        return newBitmap;
    }

    /**
     * attach Round Corner to Bitmap
     *
     * @return Bitmap
     */
    public static Bitmap attachRoundCornerToBitmap(Bitmap bitmap) {
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        Bitmap outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(outputBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, width, height);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.TRANSPARENT);
        canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return outputBitmap;
    }

    /**
     * scale Bitmap to specified width and height
     *
     * @return Bitmap
     */
    public static Bitmap scaleImageTo(Bitmap originBitmap, int newWidth, int newHeight) {
        return scaleImage(originBitmap, (float) newWidth / originBitmap.getWidth(), (float) newHeight / originBitmap.getHeight());
    }

    /**
     * scale Bitmap
     *
     * @return Bitmap
     */
    public static Bitmap scaleImage(Bitmap originBitmap, float scaleWidth, float scaleHeight) {
        if (originBitmap == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(originBitmap, 0, 0, originBitmap.getWidth(), originBitmap.getHeight(), matrix, true);
    }

    /**
     * convert Bitmap to byte array
     *
     * @return byte[]
     */
    public static byte[] bitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     *
     * @return Bitmap
     */
    public static Bitmap byteToBitmap(byte[] bytes) {
        return (bytes == null || bytes.length == 0) ? null : BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * convert Bitmap to Base64 encode String
     *
     * @return String
     */
    public static String bitmapToString(Bitmap bitmap) {
        return Base64.encodeToString(bitmapToByte(bitmap), Base64.DEFAULT);
    }

    /**
     * convert Drawable to Bitmap
     *
     * @return Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     *
     * @return Drawable
     */
    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        return bitmap == null ? null : new BitmapDrawable(bitmap);
    }
}
