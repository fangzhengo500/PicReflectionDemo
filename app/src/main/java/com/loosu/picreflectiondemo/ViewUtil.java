package com.loosu.picreflectiondemo;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;

public class ViewUtil {
    private ViewUtil() {
        // util no instance.
    }

    /**
     * 获取view的图像
     *
     * @param view view
     * @return bitmap
     */
    @Nullable
    public static Bitmap getBitmapByView(View view) {
        Bitmap result = null;
        try {
            view.setDrawingCacheEnabled(true);
            Bitmap drawingCache = view.getDrawingCache();
            result = drawingCache.copy(Bitmap.Config.ARGB_8888, true);
            view.setDrawingCacheEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
