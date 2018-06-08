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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.panpan.hantaimi.entity.hanjuzong.HanjuleiInfo;
import com.panpan.hantaimi.module.home.hanjuzong.NewsDetailActivity;
import com.panpan.hantaimi.module.home.hanjuzong.NewshanjuActivity;
import com.panpan.hantanmi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class HanjuleiAdapter extends RecyclerView.Adapter{
    private Context context;
    private HanjuleiInfo mHanjuleiInfo;
    private int count;


    public HanjuleiAdapter(Context context) {
        this.context = context;
    }


    public void sethanjuleiInfo(HanjuleiInfo mHanjuleiInfo) {
        this. mHanjuleiInfo = mHanjuleiInfo;
        count=mHanjuleiInfo.getData().getDongtaicount();
    }


    @SuppressLint("InflateParams")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_home_bangumi_new_serial_body, null);
        return new HanjuleiAdapter.gengxtvItemViewHolder(view);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       HanjuleiAdapter.gengxtvItemViewHolder liveItemViewHolder = (HanjuleiAdapter.gengxtvItemViewHolder) holder;
        Glide.with(context)
                .load(mHanjuleiInfo.getData().getDongtai().get(position).getImg())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(liveItemViewHolder.mImage);
        liveItemViewHolder.mTitle.setText(mHanjuleiInfo.getData().getDongtai().get(position).getTitle());
        liveItemViewHolder.mPlay.setText(mHanjuleiInfo.getData().getDongtai().get(position).getdata());
        liveItemViewHolder.mCardView.setOnClickListener(v -> NewshanjuActivity.
                launch((Activity) context,mHanjuleiInfo.getData().getDongtai().get(position).getLink()));
    }
    public int getSpanSize() {
        return 4;

    }
    @Override
    public int getItemCount() {
        if (mHanjuleiInfo!= null) {
            return count;
        } else {
            return 0;
        }
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    static class gengxtvItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view)
        CardView mCardView;

        @BindView(R.id.item_img)
        ImageView mImage;

        @BindView(R.id.item_title)
        TextView mTitle;

        @BindView(R.id.item_play)
        TextView mPlay;



        public  gengxtvItemViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
