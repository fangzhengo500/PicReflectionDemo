package com.loosu.picreflectiondemo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.loosu.picreflectiondemo.R;
import com.loosu.picreflectiondemo.utils.BitmapUtil;
import com.loosu.picreflectiondemo.utils.ViewUtil;

public class ReflectLayout extends FrameLayout {
    private static final String TAG = "ReflectLayout";

    public ReflectLayout(@NonNull Context context) {
        this(context, null);
    }

    public ReflectLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReflectLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() != View.VISIBLE) {
                continue;
            }

            ViewGroup.LayoutParams params = childAt.getLayoutParams();
            if (params instanceof LayoutParams) {
                LayoutParams layoutParams = (LayoutParams) params;
                boolean reflect = layoutParams.reflectAble;         // 开启阴影效果
                float reflectHeight = layoutParams.reflectHeight;   // 阴影高度
                float reflectSpace = layoutParams.reflectSpace;     // 阴影间隔

                float viewLeft = childAt.getLeft() + childAt.getTranslationX();         // view 的视图位置 left
                float viewBottom = childAt.getBottom() + childAt.getTranslationY();     // view 的视图位置 bottom

                if (reflect) {
                    // 启用阴影效果

                    // 计算实际的阴影高度
                    int shadowHeight = (int) reflectHeight;
                    if (reflectHeight == LayoutParams.MATCH_PARENT) {
                        shadowHeight = (int) (getHeight() - getPaddingBottom() - (viewBottom + reflectSpace));
                    }

                    // 绘制阴影效果
                    Bitmap source = ViewUtil.getBitmapByView(childAt);
                    Bitmap shadow = BitmapUtil.createBitmapShadow(source, shadowHeight);
                    if (shadow != null) {
                        canvas.drawBitmap(shadow, viewLeft, viewBottom + reflectSpace, null);
                    }
                } else {
                    // do nothing
                }
            }
        }
    }

    @Override
    protected ReflectLayout.LayoutParams generateDefaultLayoutParams() {
        return new ReflectLayout.LayoutParams(ReflectLayout.LayoutParams.MATCH_PARENT, ReflectLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return new ReflectLayout.LayoutParams(lp);
    }

    @Override
    public ReflectLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new ReflectLayout.LayoutParams(getContext(), attrs);
    }

    public static class LayoutParams extends FrameLayout.LayoutParams {
        public boolean reflectAble = false;
        public float reflectHeight = 0;
        private float reflectSpace = 0;

        public LayoutParams(@NonNull Context c, @Nullable AttributeSet attrs) {
            super(c, attrs);

            final TypedArray a = c.obtainStyledAttributes(attrs, R.styleable.ReflectLayout_Layout);
            reflectAble = a.getBoolean(R.styleable.ReflectLayout_Layout_reflect, false);
            reflectHeight = a.getLayoutDimension(R.styleable.ReflectLayout_Layout_reflect_height, "reflect_height");
            //reflectHeight = a.getDimension(R.styleable.ReflectLayout_Layout_reflect_height, MATCH_PARENT);
            reflectSpace = a.getDimension(R.styleable.ReflectLayout_Layout_reflect_space, 0);
            a.recycle();
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(int width, int height, int gravity) {
            super(width, height, gravity);
        }

        public LayoutParams(@NonNull ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
