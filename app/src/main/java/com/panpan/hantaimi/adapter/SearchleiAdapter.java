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
import com.panpan.hantaimi.entity.SearchArchiveInfo;
import com.panpan.hantaimi.entity.hanjuzong.HanjuleiInfo;
import com.panpan.hantaimi.module.home.hanjuzong.NewsDetailActivity;
import com.panpan.hantaimi.module.home.hanjuzong.NewshanjuActivity;
import com.panpan.hantanmi.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class SearchleiAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<SearchArchiveInfo> mSearchArchiveInfo;
    private int count;
    private int[] leiicons = new int[]{
            R.drawable.ic_video,
            R.drawable.ic_news,
            R.drawable.ic_picture
    };

    public SearchleiAdapter(Context context) {
        this.context = context;
    }


    public void setSearchArchiveInfo(List<SearchArchiveInfo> mSearchArchiveInfo) {
        this. mSearchArchiveInfo = mSearchArchiveInfo;
        count=mSearchArchiveInfo.size();
    }


    @SuppressLint("InflateParams")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_search_lei, null);
        return new  SearchleiAdapter.SearchViewHolder(view);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchleiAdapter.SearchViewHolder liveItemViewHolder = (SearchleiAdapter.SearchViewHolder) holder;
        int xuan=mSearchArchiveInfo.get(position).getType();
        Glide.with(context)
                .load(leiicons[xuan])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(liveItemViewHolder.Searchitemimg);
        liveItemViewHolder.SearchTitle.setText(mSearchArchiveInfo.get(position).gettitle());
        liveItemViewHolder.Searchjiansu.setText(mSearchArchiveInfo.get(position).getcontent());
        liveItemViewHolder.Searchdata.setText(mSearchArchiveInfo.get(position).gettime());
        if(xuan==0){
            liveItemViewHolder.mCardView.setOnClickListener(v -> NewshanjuActivity.
                    launch((Activity) context,mSearchArchiveInfo.get(position).getLink()));
        }
        else if(xuan==1)
        {
            liveItemViewHolder.mCardView.setOnClickListener(v -> NewsDetailActivity.
                    launch((Activity) context,mSearchArchiveInfo.get(position).gettitle(),
                            mSearchArchiveInfo.get(position).getLink(),
                            null,"wen"));
        }
        else if(xuan==2){
            liveItemViewHolder.mCardView.setOnClickListener(v -> NewsDetailActivity.
                    launch((Activity) context,mSearchArchiveInfo.get(position).gettitle(),
                            mSearchArchiveInfo.get(position).getLink(),
                            null,"tu"));
        }
    }
    public int getSpanSize() {
        return 12;

    }
    @Override
    public int getItemCount() {
        if (mSearchArchiveInfo!= null) {
            return count;
        } else {
            return 0;
        }
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }


    static class SearchViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.search_item_img)
        ImageView Searchitemimg;
        @BindView(R.id.search_item_title)
        TextView SearchTitle;
        @BindView(R.id.search_item_review)
        TextView Searchjiansu;
        @BindView(R.id.search_item_data)
        TextView Searchdata;
        @BindView(R.id.search_card_view)
        CardView mCardView;


        SearchViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

