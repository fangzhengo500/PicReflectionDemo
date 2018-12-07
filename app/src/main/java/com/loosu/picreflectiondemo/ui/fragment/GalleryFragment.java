package com.loosu.picreflectiondemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.leochuan.CarouselLayoutManager;
import com.leochuan.CenterSnapHelper;
import com.loosu.picreflectiondemo.R;
import com.loosu.picreflectiondemo.utils.ViewUtil;

public class GalleryFragment extends Fragment {

    private RecyclerView mViewList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view, savedInstanceState);
        initView(view, savedInstanceState);
        initListener(view, savedInstanceState);
    }


    private void findView(@NonNull View view, Bundle savedInstanceState) {
        mViewList = view.findViewById(R.id.view_list);
    }

    private void initView(View view, Bundle savedInstanceState) {
        mViewList.post(new Runnable() {
            @Override
            public void run() {
                int maxVisibleItemCount = 5;
                int itemWidth = ViewUtil.dp2px(getContext(), 400);
                int measuredWidth = mViewList.getWidth();
                int itemSpace = (int) (((maxVisibleItemCount * itemWidth) - measuredWidth) / (maxVisibleItemCount - 1) * 0.85);
                CarouselLayoutManager layoutManager = new CarouselLayoutManager.Builder(getContext(), itemSpace)
                        .setMaxVisibleItemCount(5)
                        .build();
                mViewList.setLayoutManager(layoutManager);
                mViewList.setAdapter(new MyAdapter(mViewList));

                CenterSnapHelper snapHelper = new CenterSnapHelper();
                snapHelper.attachToRecyclerView(mViewList);
            }
        });
    }

    private void initListener(View view, Bundle savedInstanceState) {

    }

    private static class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private final int[] pics = {
                R.mipmap.pic2,
                R.mipmap.pic3,
        };

        private final RecyclerView mRecyclerView;

        public MyAdapter(RecyclerView recyclerView) {
            mRecyclerView = recyclerView;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery, viewGroup, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
            holder.ivCover.setImageResource(pics[i % pics.length]);

            if (mRecyclerView.getLayoutManager() instanceof CarouselLayoutManager) {
                int currentPosition = ((CarouselLayoutManager) mRecyclerView.getLayoutManager()).getCurrentPosition();
                holder.btnPlay.setVisibility(currentPosition == i ? View.VISIBLE : View.GONE);
                holder.layoutToolbar.setVisibility(currentPosition == i ? View.VISIBLE : View.GONE);
                holder.layoutBottom.setVisibility(currentPosition == i ? View.VISIBLE : View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return 20;
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        View btnPlay;
        View layoutToolbar;
        View layoutBottom;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            btnPlay = itemView.findViewById(R.id.btn_play);
            layoutToolbar = itemView.findViewById(R.id.layout_item_toolbar);
            layoutBottom = itemView.findViewById(R.id.layout_item_bottom);
        }
    }
}
