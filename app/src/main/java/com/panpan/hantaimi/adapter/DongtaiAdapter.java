package com.panpan.hantaimi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.panpan.hantaimi.entity.hanjuzong.dongtaiInfo;
import com.panpan.hantaimi.module.home.hanjuzong.DongtaiFragment;
import com.panpan.hantaimi.module.home.hanjuzong.NewsDetailActivity;

import com.panpan.hantanmi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class DongtaiAdapter extends RecyclerView.Adapter{
    private Context context;
    private  dongtaiInfo mdongtaiInfo;
    private int count;

    public DongtaiAdapter(Context context) {
        this.context = context;
    }


    public void setdongtaiInfo( dongtaiInfo dongtaiInfo) {
        this. mdongtaiInfo = dongtaiInfo;
        count=mdongtaiInfo.getData().getDongtaicount();
    }


    @SuppressLint("InflateParams")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_dongtai_lie, null);
        return new DongtaiAdapter.dongtaiViewHolder(view);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DongtaiAdapter.dongtaiViewHolder liveItemViewHolder = (DongtaiAdapter.dongtaiViewHolder) holder;
            Glide.with(context)
                    .load(mdongtaiInfo.getData().getDongtai().get(position).getImg())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(liveItemViewHolder.dongtaiitemimg);
            liveItemViewHolder.itemdongtaiTitle.setText(mdongtaiInfo.getData().getDongtai().get(position).getTitle());
        liveItemViewHolder.itemdongtaijiansu.setText(mdongtaiInfo.getData().getDongtai().get(position).getjiansu());
        liveItemViewHolder.itemdongtaidata.setText("更新时间："+"20"+mdongtaiInfo.getData().getDongtai().get(position).getdata());
        liveItemViewHolder.itemLiveLayout.setOnClickListener(v -> NewsDetailActivity.
                launch((Activity) context,mdongtaiInfo.getData().getDongtai().get(position).getTitle(),
                        mdongtaiInfo.getData().getDongtai().get(position).getLink(),
                        mdongtaiInfo.getData().getDongtai().get(position).getImg(),"wen"));

    }
    public int getSpanSize() {
                return 12;

    }
    @Override
    public int getItemCount() {
        if (mdongtaiInfo != null) {
            return count;
        } else {
            return 0;
        }
    }
    @Override
    public int getItemViewType(int position) {
                return 0;
    }


    static class dongtaiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.dongtai_item_img)
        ImageView dongtaiitemimg;
        @BindView(R.id.dongtai_item_title)
        TextView itemdongtaiTitle;
        @BindView(R.id.dongtai_item_review)
        TextView itemdongtaijiansu;
        @BindView(R.id.dongtai_item_data)
        TextView itemdongtaidata;
        @BindView(R.id.dongtai_card_view)
        CardView itemLiveLayout;


        dongtaiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
