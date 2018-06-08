package com.panpan.hantaimi.gsyvideoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.panpan.hantaimi.adapter.HanjujishuAdapter;
import com.panpan.hantaimi.entity.HistoryInfo;
import com.panpan.hantaimi.entity.hanjuzong.NewshanjuInfo;
import com.panpan.hantaimi.utils.ConstantUtil;
import com.panpan.hantanmi.R;
import com.shuyu.gsyvideoplayer.GSYBaseActivityDetail;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.model.GSYVideoModel;
import com.shuyu.gsyvideoplayer.video.ListGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DetailADPlayer extends GSYBaseActivityDetail<PlayerView> {
    @BindView(R.id.post_detail_nested_scroll)
    NestedScrollView postDetailNestedScroll;
    @BindView(R.id.ad_player)
    PlayerView detailPlayer;
    @BindView(R.id.activity_detail_player)
    RelativeLayout activityDetailPlayer;
    @BindView(R.id.news_hanju_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.play_zongji)
    TextView mTextView;


    private String jishu;
    private String title;
    private int tit;
    private String img;
    private String link;
    private List<String> zonglink;
   private NewshanjuInfo mNewshanjuInfo;
    private dHanjujishuAdapter mHanjujishuAdapter ;
   private  List<GSYVideoModel> urls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ad_playyer);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent != null) {
            mNewshanjuInfo=intent.getParcelableExtra(ConstantUtil.EXTRA_TEST);
            tit = intent.getIntExtra(ConstantUtil.EXTRA_tit,tit);
            loadcontent();
            initsql();
            initRecyclerView();
            mHanjujishuAdapter.sethanjujishuInfo(mNewshanjuInfo);
            mHanjujishuAdapter .notifyDataSetChanged();
            mRecyclerView.scrollToPosition(0);
            inttdetailPlayer();
        }
    }

    private void inttdetailPlayer(){
        //普通模式
        initVideo();
        urls  = new ArrayList<>();
        int ji=1;
        for(String link:zonglink) {
            urls.add(new GSYVideoModel(link, title + " " +"第"+ji+"集"));
            ji++;
        }
        detailPlayer.setUp(urls, true, tit);
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this)
                .load(img)
                .into( imageView);
        detailPlayer.setThumbImageView(imageView);

        resolveNormalVideoUI();
        detailPlayer.setIsTouchWiget(true);
        //关闭自动旋转
        detailPlayer.setRotateViewAuto(false);
        detailPlayer.setLockLand(false);
        detailPlayer.setShowFullAnimation(false);
        detailPlayer.setNeedLockFull(true);
        detailPlayer.setVideoAllCallBack(this);


        detailPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        });
    }

    private void loadcontent() {
        title = mNewshanjuInfo.getTitle();
        img=mNewshanjuInfo.getImg();
        link=mNewshanjuInfo.getLink();
        zonglink= mNewshanjuInfo.getZonglink();
        String useName=mNewshanjuInfo.getContent();
        int begin=useName.indexOf("</script></span>");
        int last=useName.indexOf("</div>");
        mWebView.loadData(getHtmlData(useName.substring(begin,last)),
                "text/html; charset=utf-8", "utf-8");
        mTextView.setText("选集：总"+zonglink.size()+"集");
    }

    private void initsql() {
        int xuan=tit+1;
        jishu="第"+xuan+"集";
       DataSupport.deleteAll(HistoryInfo.class,
                "tvid = ? ", link);
        HistoryInfo mHistoryInfo = new HistoryInfo();
        mHistoryInfo.setTvid(link);
        mHistoryInfo.setJishuid(jishu);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        mHistoryInfo.setSeetime(date);
        mHistoryInfo.save();
    }

   private void initRecyclerView() {
       mHanjujishuAdapter = new dHanjujishuAdapter(DetailADPlayer.this);
       mRecyclerView.setAdapter(mHanjujishuAdapter);
       GridLayoutManager layout = new GridLayoutManager(this, 12);
       layout.setOrientation(LinearLayoutManager.VERTICAL);
       layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
           @Override
           public int getSpanSize(int position) {
               return mHanjujishuAdapter.getSpanSize();
           }
       });
       mRecyclerView.setLayoutManager(layout);
    }


    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body><div ><script type=\"text/javascript\">" + bodyHTML +
                "</div>来源:<a href=\"http://www.hanju.cc\" target=\"_blank\">韩剧网</a>" +
                " http://www.hanju.cc</div></div></body></html>";
    }
    @OnClick({ R.id.back})
    public void sayHi(ImageView mImageView) {
        switch (mImageView.getId()) {
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }
    @Override
    public PlayerView getGSYVideoPlayer() {
        return detailPlayer;
    }

    @Override
    public GSYVideoOptionBuilder getGSYVideoOptionBuilder() {
        //不需要builder的
        return null;
    }

    @Override
    public void clickForFullScreen() {

    }

    /**
     * 是否启动旋转横屏，true表示启动
     *
     * @return true
     */
    @Override
    public boolean getDetailOrientationRotateAuto() {
        return true;
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        super.onEnterFullscreen(url, objects);
        //隐藏调全屏对象的返回按键
        GSYVideoPlayer gsyVideoPlayer = (GSYVideoPlayer) objects[1];
        gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
    }


    private void resolveNormalVideoUI() {
        //增加title
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
    }

    public static void launch(Activity activity,  int ji,
                               NewshanjuInfo mNewshanjuInfo) {
      Intent  mIntent = new Intent(activity, DetailADPlayer.class);
        mIntent.putExtra(ConstantUtil.EXTRA_TEST,mNewshanjuInfo);
        mIntent.putExtra(ConstantUtil.EXTRA_tit,ji);
       activity.startActivity(mIntent);
    }

    public class dHanjujishuAdapter extends RecyclerView.Adapter{
        private Context context;
        private NewshanjuInfo mNewshanjuInfo;

        public dHanjujishuAdapter(Context context) {
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
            int ss=position+1;
            String jishu="第"+ss+"集";
            liveItemViewHolder.mButton.setText(jishu);
            liveItemViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PlayerView)detailPlayer.getCurrentPlayer()).playNext(position);
                    Log.i("hanju", "onClick: "+zonglink.get(position));
                    tit=position;
                    initsql();
                }
            }
            );}
        public int getSpanSize() {
            return 3;

        }
        @Override
        public int getItemCount() {
            if (mNewshanjuInfo!= null) {
                return mNewshanjuInfo.getZonglink().size();
            } else {
                return 0;
            }
        }
        @Override
        public int getItemViewType(int position) {
            return 0;
        }

    }



}

