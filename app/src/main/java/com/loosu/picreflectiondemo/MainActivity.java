package com.loosu.picreflectiondemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ImageView mIvSource;
    private ImageView mIvShadow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView(savedInstanceState);
        initView(savedInstanceState);
    }


    private void findView(Bundle savedInstanceState) {
        mIvSource = findViewById(R.id.iv_source);
        mIvShadow = findViewById(R.id.iv_shadow);
    }

    private void initView(Bundle savedInstanceState) {
        mIvSource.post(new Runnable() {
            @Override
            public void run() {
                Bitmap source = ViewUtil.getBitmapByView(mIvSource);
                Bitmap shadow = BitmapUtil.createBitmapShadow(source, source.getHeight());
                mIvShadow.setImageBitmap(shadow);
            }
        });
    }
}
