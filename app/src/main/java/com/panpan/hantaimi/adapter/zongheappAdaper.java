package com.panpan.hantaimi.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.panpan.hantaimi.entity.hanjuzong.zongheInfo;
import com.panpan.hantaimi.module.home.hanjuzong.NewsDetailActivity;
import com.panpan.hantaimi.module.home.hanjuzong.NewshanjuActivity;
import com.panpan.hantaimi.widget.banner.BannerEntity;
import com.panpan.hantaimi.widget.banner.BannerView;
import com.panpan.hantanmi.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public  class zongheappAdaper extends RecyclerView.Adapter{
    private Context context;
    private zongheInfo mzongheAppIndexInfo;
    private int entranceSize;

    //直播Item
    private static final int TYPE_LIVE_ITEM = 0;
    //直播分类Title
    private static final int TYPE_PARTITION = 1;
    //直播页Banner
    private static final int TYPE_BANNER = 2;
    private static final int TYPE_gengxintv_ITEM = 3;
    private static final int TYPE_dongtai_ITEM=4;
    private static final int TYPE_dongtaiwen_ITEM=5;
    private List<BannerEntity> bannerEntitys = new ArrayList<>();
    private List<Integer> liveSizes = new ArrayList<>();
    int x,x1,x2,x3;
    private int[] leiicons = new int[]{
            R.drawable.ic_video,
            R.drawable.live_home_all_category,
            R.drawable.ic_news,
            R.drawable.ic_picture
    };
    private String[] leiTitles = new String[]{
            "最近更新", "每周一星",
            "动态", "图库"
    };
    public zongheappAdaper(Context context) {
        this.context = context;
    }


    public void setLiveInfo(zongheInfo  mzongheAppIndexInfo) {
        this. mzongheAppIndexInfo =  mzongheAppIndexInfo;
        entranceSize = 4;
        liveSizes.clear();
        bannerEntitys.clear();
        List<zongheInfo.DataBean.BannerBean> banner =  mzongheAppIndexInfo.getData().getBanner();
        Observable.from(banner)
                .forEach(bannerBean -> bannerEntitys.add(new BannerEntity(
                        bannerBean.getLink(), bannerBean.getTitle(), bannerBean.getImg())));
         x=mzongheAppIndexInfo.getData().getgengxtvcount();
         x1=mzongheAppIndexInfo.getData().getzhouxingcount();
         x2=mzongheAppIndexInfo.getData().getdongtcount();
        x3=mzongheAppIndexInfo.getData().getpiccount();
        liveSizes.add(x);
        liveSizes.add(x+x1);
        liveSizes.add(x+x1+x2);
        liveSizes.add(x+x1+x2+x3);
        Log.i("getNewsItems:" , String.valueOf(liveSizes.get(0))+"hh"+String.valueOf(liveSizes.get(1))+"hh");
    }


    @SuppressLint("InflateParams")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_LIVE_ITEM:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_zonghe_zhan, null);
                return new zongheappAdaper.zongheViewHolder(view);
            case TYPE_PARTITION:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_live_partition_title, null);
                return new zongheappAdaper.zongheleiViewHolder(view);
            case TYPE_BANNER:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_live_banner, null);
                return new zongheappAdaper.zongheBannerViewHolder(view);
            case TYPE_gengxintv_ITEM:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.layout_home_bangumi_new_serial_body, null);
                return new zongheappAdaper. gengxtvItemViewHolder(view);
            case TYPE_dongtai_ITEM:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_zonghe_zhan, null);
                return new zongheappAdaper.dongtaiViewHolder(view);
            case TYPE_dongtaiwen_ITEM:
                view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.item_zonghe_dongtwen, null);
                return new zongheappAdaper.dongtaiwenViewHolder(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String img="";
        String tittle="";
        String tit="";
        String link="";
        int leix=1;
        if(position!=0)
        for(int i=0;i<4;i++){
            for(int j=i==0?1:liveSizes.get(i-1)+i+1;j<=liveSizes.get(i)+i+1;j++)
            {
                if (position==j) {
                    switch (i) {
                        case 0:
                            if(position==1)
                                leix=0;
                            else{
                            int xuan=position-2;
                            img = mzongheAppIndexInfo.getData().getgengxtv().get(xuan).getImg();
                            tittle = mzongheAppIndexInfo.getData().getgengxtv().get(xuan).getTitle();
                            tit = mzongheAppIndexInfo.getData().getgengxtv().get(xuan).gettit();
                            link=mzongheAppIndexInfo.getData().getgengxtv().get(xuan).getLink();}
                            break;
                        case 1:
                            if(position==liveSizes.get(0)+2)
                                leix=1;
                            else{
                            int xuan1=position-liveSizes.get(0)-3;
                            img = mzongheAppIndexInfo.getData().getzhouxing().get(xuan1).getImg();
                            tittle = mzongheAppIndexInfo.getData().getzhouxing().get(xuan1).getTitle();
                                link=mzongheAppIndexInfo.getData().getzhouxing().get(xuan1).getLink();}
                            break;
                        case 2:
                            if(position==liveSizes.get(1)+3)
                                leix=2;
                            else{
                            int xuan2=position-liveSizes.get(1)-4;
                            img = mzongheAppIndexInfo.getData().getdongt().get(xuan2).getImg();
                            tittle = mzongheAppIndexInfo.getData().getdongt().get(xuan2).getTitle();
                                link=mzongheAppIndexInfo.getData().getdongt().get(xuan2).getLink();}
                            break;
                        case 3:
                            if(position==liveSizes.get(2)+4)
                                leix=3;
                            else{
                            int xuan3=position-liveSizes.get(2)-5;
                            img = mzongheAppIndexInfo.getData().getpic().get(xuan3).getImg();
                            tittle = mzongheAppIndexInfo.getData().getpic().get(xuan3).getTitle();
                                link=mzongheAppIndexInfo.getData().getpic().get(xuan3).getLink();}
                            break;

                    }
                }
            }
        }
        String finalLink = link;
        String finalTittle = tittle;
        String finalImg = img;

        if (holder instanceof zongheappAdaper.zongheViewHolder) {
            zongheappAdaper.zongheViewHolder liveItemViewHolder = (zongheappAdaper.zongheViewHolder) holder;
            Glide.with(context)
                    .load(img)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(liveItemViewHolder.itemzongheCover);
            liveItemViewHolder.itemzongheTitle.setText(tittle);
            liveItemViewHolder.mCardView.setOnClickListener(v -> NewsDetailActivity.
                    launch((Activity) context, finalTittle,
                            finalLink,
                            finalImg,"tu"));
        } else if (holder instanceof zongheappAdaper.zongheleiViewHolder) {
            zongheappAdaper.zongheleiViewHolder mzongheleiViewHolder = (zongheappAdaper.zongheleiViewHolder) holder;
            Glide.with(context)
                    .load(leiicons[leix])
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(mzongheleiViewHolder.image);
            mzongheleiViewHolder.title.setText(leiTitles[leix]);
        } else if (holder instanceof zongheappAdaper.zongheBannerViewHolder) {
            zongheappAdaper.zongheBannerViewHolder liveBannerViewHolder = (zongheappAdaper.zongheBannerViewHolder) holder;
            liveBannerViewHolder.banner
                    .delayTime(5)
                    .build(bannerEntitys);
        }else if(holder instanceof zongheappAdaper.gengxtvItemViewHolder){
            zongheappAdaper.gengxtvItemViewHolder liveItemViewHolder = (zongheappAdaper.gengxtvItemViewHolder) holder;
            Glide.with(context)
                    .load(img)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(liveItemViewHolder.mImage);
            liveItemViewHolder.mTitle.setText(tittle);
            liveItemViewHolder.mPlay.setText(tit);
            liveItemViewHolder.mCardView.setOnClickListener(v -> NewshanjuActivity.
                    launch((Activity) context,finalLink));
        }else if(holder instanceof zongheappAdaper.dongtaiViewHolder){
            zongheappAdaper.dongtaiViewHolder liveItemViewHolder = (zongheappAdaper.dongtaiViewHolder) holder;
            Glide.with(context)
                    .load(img)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(liveItemViewHolder.itemzongheCover);
            liveItemViewHolder.itemzongheTitle.setText(tittle);
            liveItemViewHolder.mCardView.setOnClickListener(v -> NewsDetailActivity.
                    launch((Activity) context, finalTittle,
                            finalLink,
                            finalImg,"wen"));
        }else if(holder instanceof zongheappAdaper.dongtaiwenViewHolder){
            zongheappAdaper.dongtaiwenViewHolder liveItemViewHolder = (zongheappAdaper.dongtaiwenViewHolder) holder;
            liveItemViewHolder.itemzongheTitle.setText(tittle);
            liveItemViewHolder.mCardView.setOnClickListener(v -> NewsDetailActivity.
                    launch((Activity) context, finalTittle,
                            finalLink,
                            finalImg,"wen"));
        }
    }
    public int getSpanSize(int pos) {
        int viewType = getItemViewType(pos);
        switch (viewType) {
            case TYPE_gengxintv_ITEM:
                return 4;
            case TYPE_LIVE_ITEM:
            case  TYPE_dongtai_ITEM:
                return 6;
            case TYPE_PARTITION:
            case  TYPE_dongtaiwen_ITEM:
                return 12;
            case TYPE_BANNER:
                return 12;
        }
        return 0;
    }
    @Override
    public int getItemCount() {
        if (mzongheAppIndexInfo != null) {
            return 5+liveSizes.get(3);
        } else {
            return 0;
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        position -= 1;
       if (isPartitionTitle(position)) {
            return TYPE_PARTITION;
        } else {
           if(position<=liveSizes.get(0))
               return TYPE_gengxintv_ITEM;
           else if(position>liveSizes.get(1)+2&&position<=liveSizes.get(1)+5)
               return TYPE_dongtai_ITEM;
           else if(position>liveSizes.get(1)+5&&position<=liveSizes.get(2)+3)
               return TYPE_dongtaiwen_ITEM;
           else
            return TYPE_LIVE_ITEM;
        }
    }


    /**
     * 获取当前Item在第几组中
     */
    private int getItemPosition(int pos) {
        pos -= entranceSize;
        return pos / 5;
    }


    private boolean isPartitionTitle(int pos) {
        int xx=liveSizes.get(0)+1;
        int xx1=liveSizes.get(1)+2;
        int xx2=liveSizes.get(2)+3;
        if(pos==0)
            return true;
        else if(pos==xx||pos==xx1||pos==xx2)
        {
            return true;
        }

        return false;
    }



    static class zongheBannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_live_banner)
        public BannerView banner;
        zongheBannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    static class dongtaiwenViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_zonghe_dongtwen_title)
        public TextView itemzongheTitle;
        @BindView(R.id.card_view_wen)
        CardView mCardView;
        dongtaiwenViewHolder (View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    static class dongtaiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_zonghe_cover)
        ImageView itemzongheCover;
        @BindView(R.id.item_zonghe_title)
        TextView itemzongheTitle;
        @BindView(R.id.item_zonghe_layout)
        CardView mCardView;

        dongtaiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class zongheleiViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_live_partition_title)
        public TextView title;
        @BindView(R.id.item_live_partition_icon)
        public ImageView image;

        zongheleiViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class zongheViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_zonghe_cover)
        ImageView itemzongheCover;
        @BindView(R.id.item_zonghe_title)
        TextView itemzongheTitle;
        @BindView(R.id.item_zonghe_layout)
        CardView mCardView;


        zongheViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
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
