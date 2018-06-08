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
import com.panpan.hantaimi.entity.hanjuzong.PicleiInfo;
import com.panpan.hantaimi.module.home.hanjuzong.NewsDetailActivity;
import com.panpan.hantanmi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class PicleiAdapter extends RecyclerView.Adapter{
private Context context;
private PicleiInfo mPicleiInfo;
private int count;


public PicleiAdapter(Context context) {
        this.context = context;
        }


public void setpicleiInfo(PicleiInfo mPicleiInfo) {
        this.mPicleiInfo = mPicleiInfo;
        count=mPicleiInfo.getData().getPicleicount();
        }


@SuppressLint("InflateParams")
@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
    view = LayoutInflater.from(viewGroup.getContext())
            .inflate(R.layout.item_zonghe_zhan, null);
        return new PicleiAdapter.PicleiViewHolder(view);

        }


@Override
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    PicleiAdapter.PicleiViewHolder liveItemViewHolder = (PicleiAdapter.PicleiViewHolder) holder;
        Glide.with(context)
        .load(mPicleiInfo.getData().getDongtai().get(position).getImg())
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.bili_default_image_tv)
        .dontAnimate()
        .into(liveItemViewHolder.image);
        liveItemViewHolder.title.setText(mPicleiInfo.getData().getDongtai().get(position).getTitle());
    liveItemViewHolder.itemLiveLayout.setOnClickListener(v -> NewsDetailActivity.
            launch((Activity) context,mPicleiInfo.getData().getDongtai().get(position).getTitle(),
                    mPicleiInfo.getData().getDongtai().get(position).getLink(),
                    mPicleiInfo.getData().getDongtai().get(position).getImg(),"tu"));

        }
public int getSpanSize() {
        return 6;

        }
@Override
public int getItemCount() {
        if (mPicleiInfo!= null) {
        return count;
        } else {
        return 0;
        }
        }
@Override
public int getItemViewType(int position) {
        return 0;
        }


    static class PicleiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_zonghe_cover)
        ImageView image;
        @BindView(R.id.item_zonghe_title)
        TextView title;
        @BindView(R.id.item_zonghe_layout)
        CardView itemLiveLayout;

        PicleiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
