package com.loosu.picreflectiondemo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.loosu.picreflectiondemo.R;
import com.loosu.picreflectiondemo.ui.fragment.GalleryFragment;
import com.loosu.picreflectiondemo.ui.fragment.ReflectLayoutFragment;

public class ReflectLayoutActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect_layout);
        mViewPager = findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
    }

    private static class MyAdapter extends FragmentPagerAdapter {

        private Fragment[] mFragments = {
                new GalleryFragment(),
                new ReflectLayoutFragment(),
        };

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments[position];
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }
    }
}
