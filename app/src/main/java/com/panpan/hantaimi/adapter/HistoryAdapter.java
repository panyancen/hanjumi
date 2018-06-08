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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.panpan.hantaimi.entity.HistoryInfo;
import com.panpan.hantaimi.entity.HistoryxianInfo;
import com.panpan.hantaimi.entity.hanjuzong.dongtaiInfo;
import com.panpan.hantaimi.module.common.MainActivity;
import com.panpan.hantaimi.module.home.hanjuzong.NewsDetailActivity;
import com.panpan.hantaimi.module.home.hanjuzong.NewshanjuActivity;
import com.panpan.hantanmi.R;

import org.litepal.crud.DataSupport;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/2 0002.
 */

public class HistoryAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<HistoryxianInfo> mHistoryxianInfo;
    private int count;

    public HistoryAdapter(Context context) {
        this.context = context;
    }


    public void setHistoryInfo(List<HistoryxianInfo> mHistoryxianInfo) {
        this.mHistoryxianInfo= mHistoryxianInfo;
        count=mHistoryxianInfo.size();
    }


    @SuppressLint("InflateParams")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_history_hanju, null);
        return new HistoryAdapter.dongtaiViewHolder(view);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int xuan=count-position-1;
        HistoryAdapter.dongtaiViewHolder liveItemViewHolder = (HistoryAdapter.dongtaiViewHolder) holder;
        Glide.with(context)
                .load(mHistoryxianInfo.get(xuan).getImg())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(liveItemViewHolder.dongtaiitemimg);
        liveItemViewHolder.itemdongtaiTitle.setText(mHistoryxianInfo.get(xuan).getTitle());
        liveItemViewHolder.itemdongtaijiansu.setText("上次看到：" +
                mHistoryxianInfo.get(xuan).getjishuid()+"/"
        +mHistoryxianInfo.get(xuan).getdai());
        liveItemViewHolder.itemdongtaidata.setText("观看时间："+mHistoryxianInfo.get(xuan).getseetime());
        liveItemViewHolder.itemLiveLayout.setOnClickListener(v -> NewshanjuActivity.
                launch((Activity) context,
                        mHistoryxianInfo.get(xuan).getLink()));
        liveItemViewHolder.historyclearimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(HistoryInfo.class,
                        "id = ? ", mHistoryxianInfo.get(xuan).getId());
                Toast.makeText(context, "删除成功", Toast.LENGTH_LONG).show();

            }
        });

    }
    public int getSpanSize() {
        return 12;

    }
    @Override
    public int getItemCount() {
        if (mHistoryxianInfo != null) {
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
        @BindView(R.id.history_item_img)
        ImageView dongtaiitemimg;
        @BindView(R.id.history_item_title)
        TextView itemdongtaiTitle;
        @BindView(R.id.history_item_review)
        TextView itemdongtaijiansu;
        @BindView(R.id.history_item_data)
        TextView itemdongtaidata;
        @BindView(R.id.history_card_view)
        CardView itemLiveLayout;
        @BindView(R.id.history_text_clear)
        ImageView historyclearimg;

        dongtaiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
