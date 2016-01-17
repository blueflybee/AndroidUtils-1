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
import android.media.ExifInterface;
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

    public static int getDegress(String path){
        int degress = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degress = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degress = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degress = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degress;
    }

    /**
     * rotate Bitmap
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress, boolean needRecycle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degress);
        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (needRecycle) {
            bitmap.recycle();
        }
        return bm;
    }

    /**
     * 图片缩放-等比缩放
     */
    public static void compress(String sourcePath, int targetWidth, int targetHeight) {
        compress(sourcePath, null, false, 0L, targetWidth, targetHeight);
    }

    /**
     * 压缩图片 （sampleSize->等比压缩）使用原路径保存文件到本地
     *
     * @param sourcePath      源文件路径
     * @param qualityCompress 是否要质量压缩
     * @param maxSize         目标图片大小
     * @param targetWidth     目标图片宽
     * @param targetHeight    目标图片高
     */
    public static void compress(String sourcePath, boolean qualityCompress, long maxSize, int targetWidth, int targetHeight) {
        compress(sourcePath, null, qualityCompress, maxSize, targetWidth, targetHeight);
    }

    /**
     * 压缩图片 （sampleSize->等比压缩）保存文件到本地
     *
     * @param sourcePath      源文件路径
     * @param targetPath      目标文件路径
     * @param qualityCompress 是否要质量压缩
     * @param maxSize         目标图片大小
     * @param targetWidth     目标图片宽
     * @param targetHeight    目标图片高
     */
    public static void compress(String sourcePath, String targetPath, boolean qualityCompress, long maxSize, int targetWidth, int targetHeight) {
        Bitmap bitmap = compress(sourcePath, targetPath, true, qualityCompress, maxSize, targetWidth, targetHeight);
        bitmap.recycle();
    }

    /**
     * 图片缩放
     *
     * @param scale 缩放倍数 >1 放大， 0..1 缩放
     */
    public static Bitmap compressBitmap(String sourcePath, float scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(sourcePath, options);
        float targetWidth = options.outWidth * scale;
        float targetHeight = options.outHeight * scale;
        return compressBitmap(sourcePath, targetWidth, targetHeight);
    }

    /**
     * 图片缩放-等比缩放
     *
     * @return Bitmap
     */
    public static Bitmap compressBitmap(String sourcePath, float targetWidth, float targetHeight) {
        return compressBitmap(sourcePath, false, 0L, targetWidth, targetHeight);
    }

    /**
     * 压缩图片 不保存，使用源文件路径
     */
    public static Bitmap compressBitmap(String sourcePath, boolean qualityCompress, long maxSize, float targetWidth, float targetHeight) {
        return compress(sourcePath, null, false, qualityCompress, maxSize, targetWidth, targetHeight);
    }

    /**
     * 压缩图片，默认等比压缩
     *
     * @param sourcePath      源文件路径
     * @param targetPath      新文件路径，null 则使用源文件路径
     * @param isSave          是否保存到新文件
     * @param qualityCompress 是否压缩质量
     * @param maxSize         压缩后最大尺寸
     * @param targetWidth     压缩后宽度
     * @param targetHeight    压缩后高度
     * @return Bitmap
     */
    public static Bitmap compress(String sourcePath, String targetPath, boolean isSave, boolean qualityCompress, long maxSize, float targetWidth, float targetHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(sourcePath, options);
        int sourceWidth = options.outWidth;
        int sourceHeight = options.outHeight;
        int inSampleSize = 1;
        while (sourceWidth / inSampleSize > targetWidth) {
            inSampleSize++;
        }
        while (sourceHeight / inSampleSize > targetHeight) {
            inSampleSize++;
        }
        if (inSampleSize <= 0) {
            inSampleSize = 1;
        }
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(sourcePath, options);
        //等比缩放
        bitmap = compressBitmap(bitmap, false, targetWidth, targetHeight);
        if (qualityCompress) {
            //质量压缩
            bitmap = compressBitmap(bitmap, true, maxSize);
        }
        if (isSave) {
            String savePath = sourcePath;
            if (!StringUtils.isEmpty(targetPath)) {
                savePath = targetPath;
            }
            saveBitmapToFile(bitmap, new File(savePath));
        }
        return bitmap;
    }

    /**
     * 等比压缩
     */
    public static Bitmap compressBitmap(Bitmap bitmap, boolean needRecycle, float targetWidth, float targetHeight) {
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
