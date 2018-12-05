package com.loosu.picreflectiondemo;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView mIvSource;
    private View mLayoutSourcePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findView(savedInstanceState);
    }

    private void findView(Bundle savedInstanceState) {
        mIvSource = findViewById(R.id.iv_source);
        mLayoutSourcePanel = findViewById(R.id.layout_source_panel);
    }

    public void onClick(View view) {
        View souceView = findViewById(R.id.layout_root);
        souceView.setDrawingCacheEnabled(true);
        Bitmap drawingCache = souceView.getDrawingCache();
        Bitmap bitmap = drawingCache.copy(Bitmap.Config.ARGB_8888, true);
        souceView.setDrawingCacheEnabled(false);

        mIvSource.setImageBitmap(bitmap);
    }
}
