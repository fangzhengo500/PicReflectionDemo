package com.loosu.picreflectiondemo;

import android.graphics.Bitmap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "MainActivity";

    //
    private ImageView mIvSource;
    private ImageView mIvShadow;
    private SeekBar mSeekShadowHeight;
    private TextView mTvShadowHeight;
    private DrawerLayout mLayoutDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView(savedInstanceState);
        initView(savedInstanceState);
        initListener(savedInstanceState);
    }


    private void findView(Bundle savedInstanceState) {
        mLayoutDrawer = findViewById(R.id.layout_drawer);

        // main content
        mIvSource = findViewById(R.id.iv_source);
        mIvShadow = findViewById(R.id.iv_shadow);

        // main menu
        mSeekShadowHeight = findViewById(R.id.seek_shadow_height);
        mTvShadowHeight = findViewById(R.id.tv_shadow_height);
    }

    private void initView(Bundle savedInstanceState) {
        mIvSource.post(new Runnable() {
            @Override
            public void run() {
                int shadowHeight = mIvSource.getHeight() / 4;
                updateImageShadow(shadowHeight);

                mSeekShadowHeight.setProgress(shadowHeight);
                mTvShadowHeight.setText(String.valueOf(shadowHeight));
            }
        });
    }

    private void updateImageShadow(int shadowHeight) {
        Bitmap source = ViewUtil.getBitmapByView(mIvSource);
        Bitmap shadow = BitmapUtil.createBitmapShadow(source, shadowHeight);
        mIvShadow.setImageBitmap(shadow);
    }

    private void initListener(Bundle savedInstanceState) {
        mSeekShadowHeight.setOnSeekBarChangeListener(this);
        mLayoutDrawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                mLayoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mLayoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
        // 点击除开侧边栏的区域会收起侧边栏。
        mLayoutDrawer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLayoutDrawer.closeDrawers();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_shadow_height:
                updateImageShadow(progress);
                mTvShadowHeight.setText(String.valueOf(progress));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
