package com.loosu.picreflectiondemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class BitmapUtil {
    public static Bitmap createBitmapShadow(Bitmap sourceBmp, int shadowHeight) {
        return createBitmapShadow(sourceBmp, shadowHeight, 255, 0);
    }

    /**
     * 创建阴影图片
     *
     * @param sourceBmp    原图
     * @param shadowHeight 阴影高度
     * @param startAlpha   Alpha component \([0..255]\) of the color
     * @param endAlpha     Alpha component \([0..255]\) of the color
     * @return 阴影图片
     */
    @Nullable
    public static Bitmap createBitmapShadow(@NonNull Bitmap sourceBmp, int shadowHeight, int startAlpha, int endAlpha) {
        int x = 0;
        int y = Math.max(0, Math.min(sourceBmp.getHeight() - shadowHeight, sourceBmp.getHeight())); // 0 <= y <= bmp.getHeight;
        int width = sourceBmp.getWidth();
        int height = Math.min(shadowHeight, sourceBmp.getHeight() - y);

        if (height <= 0) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.setScale(1, -1);  // 矩阵 matrix.setScale(1,-1) 上下翻转.
        Bitmap shadowBmp = Bitmap.createBitmap(sourceBmp, x, y, width, height, matrix, false);

        LinearGradient shader = new LinearGradient(0, 0, 0, shadowBmp.getHeight(),
                Color.argb(startAlpha, 0, 0, 0),
                Color.argb(endAlpha, 0, 0, 0),
                Shader.TileMode.MIRROR);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        Canvas canvas = new Canvas(shadowBmp);
        canvas.drawRect(canvas.getClipBounds(), paint);

        return shadowBmp;
    }
}