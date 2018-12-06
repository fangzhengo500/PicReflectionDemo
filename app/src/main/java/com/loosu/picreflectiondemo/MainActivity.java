package com.loosu.picreflectiondemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShadowBmp(View view) {
        Intent intent = new Intent(this, ShadowBitmapActivity.class);
        startActivity(intent);
    }

    public void onClickInvertedBmp(View view) {
        Intent intent = new Intent(this, InvertedBitmapActivity.class);
        startActivity(intent);
    }
}
