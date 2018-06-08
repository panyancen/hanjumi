package com.panpan.hantaimi.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.panpan.hantaimi.entity.hanjuzong.NewshanjuInfo;
import com.panpan.hantaimi.gsyvideoplayer.DetailADPlayer;
import com.panpan.hantaimi.module.home.hanjuzong.NewshanjuActivity;
import com.panpan.hantanmi.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/29 0029.
 */

public class HanjujishuAdapter extends RecyclerView.Adapter{
    private Context context;
    private NewshanjuInfo mNewshanjuInfo;

    public HanjujishuAdapter(Context context) {
        this.context = context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hanju_jishu, null);
        return new HanjujishuAdapter.hanjujishuItemViewHolder(view);
    }

    public void sethanjujishuInfo(NewshanjuInfo mNewshanjuInfo) {
        this. mNewshanjuInfo = mNewshanjuInfo;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HanjujishuAdapter.hanjujishuItemViewHolder liveItemViewHolder = (HanjujishuAdapter.hanjujishuItemViewHolder) holder;
        liveItemViewHolder.mButton.setText(mNewshanjuInfo.getjishu().get(position).getTitle());
        liveItemViewHolder.mButton.setOnClickListener(v -> DetailADPlayer.
                launch((Activity) context,position,mNewshanjuInfo));
    }
    public int getSpanSize() {
        return 3;

    }
    @Override
    public int getItemCount() {
        if (mNewshanjuInfo!= null) {
            return mNewshanjuInfo.getjishu().size();
        } else {
            return 0;
        }
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }
    public static class hanjujishuItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hanju_jishu_card)
        CardView mCardView;
        @BindView(R.id.tv_hanju_jishu)
        public
        Button mButton;
        public  hanjujishuItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
