package com.loosu.picreflectiondemo.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loosu.picreflectiondemo.R;
import com.loosu.picreflectiondemo.utils.BitmapUtil;
import com.loosu.picreflectiondemo.utils.ViewUtil;

public class ShadowBitmapActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "ShadowBitmapActivity";

    private DrawerLayout mLayoutDrawer;

    // main content
    private ImageView mIvSource;
    private ImageView mIvShadow;

    // main menu
    private SeekBar mSeekShadowHeight;
    private TextView mTvShadowHeight;
    private SeekBar mSeekStartAlpha;
    private TextView mTvStartAlpha;
    private SeekBar mSeekEndAlpha;
    private TextView mTvEndAlpha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_bmp);
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

        mSeekStartAlpha = findViewById(R.id.seek_start_alpha);
        mTvStartAlpha = findViewById(R.id.tv_start_alpha);

        mSeekEndAlpha = findViewById(R.id.seek_end_alpha);
        mTvEndAlpha = findViewById(R.id.tv_end_alpha);
    }

    private void initView(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mLayoutDrawer, 0, 0);
        mLayoutDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mIvSource.post(new Runnable() {
            @Override
            public void run() {
                int shadowHeight = mIvSource.getHeight();
                int startAlpha = 255;
                int endAlpha = 0;
                updateImageShadow(shadowHeight, startAlpha, endAlpha);

                mSeekShadowHeight.setProgress(shadowHeight);
                mTvShadowHeight.setText(String.valueOf(shadowHeight));

                mSeekStartAlpha.setProgress(startAlpha);
                mTvStartAlpha.setText(String.valueOf(startAlpha));

                mSeekEndAlpha.setProgress(endAlpha);
                mTvEndAlpha.setText(String.valueOf(endAlpha));
            }
        });
    }

    private void updateImageShadow(int shadowHeight, int startAlpha, int endAlpha) {
        Bitmap source = ViewUtil.getBitmapByView(mIvSource);
        Bitmap shadow = BitmapUtil.createBitmapShadow(source, shadowHeight, startAlpha, endAlpha);
        mIvShadow.setImageBitmap(shadow);
    }

    private void initListener(Bundle savedInstanceState) {
        mLayoutDrawer.addDrawerListener(mDrawerListener);
        mLayoutDrawer.setOnTouchListener(mDrawerTouchListener);

        mSeekShadowHeight.setOnSeekBarChangeListener(this);
        mSeekStartAlpha.setOnSeekBarChangeListener(this);
        mSeekEndAlpha.setOnSeekBarChangeListener(this);

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_shadow_height:
                updateImageShadow(mSeekShadowHeight.getProgress(), mSeekStartAlpha.getProgress(), mSeekEndAlpha.getProgress());
                mTvShadowHeight.setText(String.valueOf(progress));
                break;
            case R.id.seek_start_alpha:
                updateImageShadow(mSeekShadowHeight.getProgress(), mSeekStartAlpha.getProgress(), mSeekEndAlpha.getProgress());
                mTvStartAlpha.setText(String.valueOf(progress));
                break;
            case R.id.seek_end_alpha:
                updateImageShadow(mSeekShadowHeight.getProgress(), mSeekStartAlpha.getProgress(), mSeekEndAlpha.getProgress());
                mTvEndAlpha.setText(String.valueOf(progress));
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    // 处理滑动冲突
    private DrawerLayout.SimpleDrawerListener mDrawerListener = new DrawerLayout.SimpleDrawerListener() {
        @Override
        public void onDrawerOpened(View drawerView) {
            mLayoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            mLayoutDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    };

    // 点击除开侧边栏的区域会收起侧边栏。
    private View.OnTouchListener mDrawerTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mLayoutDrawer.closeDrawers();
                    break;
            }
            return false;
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mLayoutDrawer.isDrawerOpen(Gravity.START)) {
                    mLayoutDrawer.closeDrawers();
                } else {
                    mLayoutDrawer.openDrawer(Gravity.START);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        if (mLayoutDrawer.isDrawerOpen(GravityCompat.START)) {
            mLayoutDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
