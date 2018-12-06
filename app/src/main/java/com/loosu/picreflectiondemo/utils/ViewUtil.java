package com.loosu.picreflectiondemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.view.View;

public class ViewUtil {
    private ViewUtil() {
        // util no instance.
    }

    /**
     * dp -> px
     *
     * @param ctx context
     * @param dp  value dp
     * @return value px
     */
    public static int dp2px(Context ctx, int dp) {
        float scale = ctx.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
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
