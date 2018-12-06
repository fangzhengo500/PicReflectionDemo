package com.loosu.picreflectiondemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ViewDragHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.loosu.picreflectiondemo.R;

public class ReflectLayoutFragment extends Fragment implements View.OnTouchListener {
    private static final String TAG = "ReflectLayoutFragment";
    private ViewDragHelper mDragHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reflect_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View view1 = view.findViewById(R.id.view_1);
        View view2 = view.findViewById(R.id.view_2);
        view1.setOnTouchListener(this);
        view2.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        v.setX(x - v.getWidth() / 2);
        v.setY(y - v.getHeight() / 2);
        return true;
    }
}
