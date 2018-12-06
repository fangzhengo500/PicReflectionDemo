package com.loosu.picreflectiondemo.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.loosu.picreflectiondemo.R;
import com.loosu.picreflectiondemo.utils.BitmapUtil;

public class InvertedBitmapActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "InvertedBitmapActivity";

    private DrawerLayout mLayoutDrawer;

    // main content
    private ImageView mIvSource;

    // main menu
    private SeekBar mSeekShadowHeight;
    private TextView mTvShadowHeight;
    private SeekBar mSeekStartAlpha;
    private TextView mTvStartAlpha;
    private SeekBar mSeekEndAlpha;
    private TextView mTvEndAlpha;
    private SeekBar mSeekSpace;
    private TextView mTvSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inverted_bmp);
        findView(savedInstanceState);
        initView(savedInstanceState);
        initListener(savedInstanceState);
    }


    private void findView(Bundle savedInstanceState) {
        mLayoutDrawer = findViewById(R.id.layout_drawer);

        // main content
        mIvSource = findViewById(R.id.iv_source);

        // main menu
        mSeekShadowHeight = findViewById(R.id.seek_shadow_height);
        mTvShadowHeight = findViewById(R.id.tv_shadow_height);

        mSeekStartAlpha = findViewById(R.id.seek_start_alpha);
        mTvStartAlpha = findViewById(R.id.tv_start_alpha);

        mSeekEndAlpha = findViewById(R.id.seek_end_alpha);
        mTvEndAlpha = findViewById(R.id.tv_end_alpha);

        mSeekSpace = findViewById(R.id.seek_space);
        mTvSpace = findViewById(R.id.tv_space);
    }

    private void initView(Bundle savedInstanceState) {
        mIvSource.post(new Runnable() {
            @Override
            public void run() {
                int shadowHeight = mIvSource.getHeight();
                int startAlpha = 255;
                int endAlpha = 0;
                float space = 0;
                updateImageShadow(shadowHeight, startAlpha, endAlpha, space);

                mSeekShadowHeight.setProgress(shadowHeight);
                mTvShadowHeight.setText(String.valueOf(shadowHeight));

                mSeekStartAlpha.setProgress(startAlpha);
                mTvStartAlpha.setText(String.valueOf(startAlpha));

                mSeekEndAlpha.setProgress(endAlpha);
                mTvEndAlpha.setText(String.valueOf(endAlpha));

                mSeekSpace.setProgress((int) (space + mSeekSpace.getMax() / 2));
                mTvSpace.setText(String.valueOf(space));
            }
        });
    }

    private void updateImageShadow(int shadowHeight, int startAlpha, int endAlpha, float space) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic2);
        Bitmap invertedBmp = BitmapUtil.createInvertedBitmap(bitmap, shadowHeight, startAlpha, endAlpha, space);
        mIvSource.setImageBitmap(invertedBmp);
    }

    private void initListener(Bundle savedInstanceState) {
        mLayoutDrawer.addDrawerListener(mDrawerListener);
        mLayoutDrawer.setOnTouchListener(mDrawerTouchListener);

        mSeekShadowHeight.setOnSeekBarChangeListener(this);
        mSeekStartAlpha.setOnSeekBarChangeListener(this);
        mSeekEndAlpha.setOnSeekBarChangeListener(this);
        mSeekSpace.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.seek_shadow_height:
                updateImageShadow(mSeekShadowHeight.getProgress(), mSeekStartAlpha.getProgress(), mSeekEndAlpha.getProgress(), mSeekSpace.getProgress() - mSeekSpace.getMax() / 2);
                mTvShadowHeight.setText(String.valueOf(progress));
                break;

            case R.id.seek_start_alpha:
                updateImageShadow(mSeekShadowHeight.getProgress(), mSeekStartAlpha.getProgress(), mSeekEndAlpha.getProgress(), mSeekSpace.getProgress() - mSeekSpace.getMax() / 2);
                mTvStartAlpha.setText(String.valueOf(progress));
                break;

            case R.id.seek_end_alpha:
                updateImageShadow(mSeekShadowHeight.getProgress(), mSeekStartAlpha.getProgress(), mSeekEndAlpha.getProgress(), mSeekSpace.getProgress() - mSeekSpace.getMax() / 2);
                mTvEndAlpha.setText(String.valueOf(progress));
                break;

            case R.id.seek_space:
                updateImageShadow(mSeekShadowHeight.getProgress(), mSeekStartAlpha.getProgress(), mSeekEndAlpha.getProgress(), mSeekSpace.getProgress() - mSeekSpace.getMax() / 2);
                mTvSpace.setText(String.valueOf(mSeekSpace.getProgress() - mSeekSpace.getMax() / 2));
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
}
