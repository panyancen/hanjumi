package com.panpan.hantaimi.module.home.hanjuzong;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.panpan.hantaimi.adapter.HanjujishuAdapter;
import com.panpan.hantaimi.base.RxBaseActivity;
import com.panpan.hantaimi.biz.NewshanjuBiz;
import com.panpan.hantaimi.biz.tvbiz;
import com.panpan.hantaimi.entity.HistoryInfo;
import com.panpan.hantaimi.entity.hanjuzong.NewshanjuInfo;
import com.panpan.hantaimi.gsyvideoplayer.DetailADPlayer;
import com.panpan.hantaimi.network.RetrofitHelper;
import com.panpan.hantaimi.utils.CommonException;
import com.panpan.hantaimi.utils.ConstantUtil;
import com.panpan.hantaimi.utils.ShareUtil;
import com.panpan.hantanmi.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NewshanjuActivity extends RxBaseActivity {
    @BindView(R.id.news_hanju_cover)
    ImageView imgview;
    @BindView(R.id.news_hanju_title)
    TextView mtitle;
    @BindView(R.id.news_hanju_dai)
    TextView mdai;
    @BindView(R.id.news_hanju_time)
    TextView mtime;
    @BindView(R.id.news_hanju_test)
    TextView mtest;
    @BindView(R.id.news_hanju_zhuyan)
    TextView mzhuyan;
    @BindView(R.id.news_hanju_recycle)
    RecyclerView mRecyclerView;

    NewshanjuInfo mNewshanjuInfo;
    private String link;
    private String linkbo;
    private String linkji;
    private String title;
    private HanjujishuAdapter mHanjujishuAdapter ;
    private List<String> zonglink;
    @Override
    public int getLayoutId() {
        return R.layout.activity_news_hanju;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            link = intent.getStringExtra(ConstantUtil.EXTRA_URL);
            mHanjujishuAdapter = new HanjujishuAdapter(NewshanjuActivity.this);
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
            gethajuUrl();
        }
    }

    @OnClick({ R.id.ic_fan_go, R.id.ic_share_go})
    public void sayHi(ImageView mImageView) {
        switch (mImageView.getId()) {
            case R.id.ic_fan_go:
                finish();
                break;
            case R.id.ic_share_go:
                ShareUtil.shareLink("http://m.hanju.cc"+link,
                        title,  NewshanjuActivity.this);
                break;

            default:
                break;
        }
    }


    private void gethajuUrl() {
        RetrofitHelper.gethanjuAPI()
                .gethantupianid(link)
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        byte[] responseBytes=responseBody.bytes();
                        String responseUrl = new String(responseBytes,"gb2312");
                        Document doc = Jsoup.parse(responseUrl);
                        NewshanjuBiz mNewshanjuBiz=new NewshanjuBiz();
                        mNewshanjuInfo=mNewshanjuBiz.NewshanjuNewsItems(doc);
                        Glide.with(this)
                                .load( mNewshanjuInfo.getImg())
                                .into(imgview);
                        title =mNewshanjuInfo.getTitle();
                        mtitle.setText(mNewshanjuInfo.getTitle());
                        mdai.setText("状态："+mNewshanjuInfo.getdai());
                        mtest.setText("简介："+mNewshanjuInfo.gettest());
                        mtime.setText("更新："+mNewshanjuInfo.gettime());
                        mzhuyan.setText("主演："+mNewshanjuInfo.getzhuyan());
                        mNewshanjuInfo.setLink(link);
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } catch (CommonException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finishTask();
                }, throwable -> initEmptyView());
    }
    @Override
    public void loadData() {
        RetrofitHelper.gethanjuAPI()
                . gethantupianid(linkji)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        byte[] responseBytes=responseBody.bytes();
                        String responseUrl = new String(responseBytes,"gb2312");
                        Document doc = Jsoup.parse(responseUrl);
                        tvbiz mtvbiz=new tvbiz();
                        linkbo=mtvbiz.getmediaplayerInfo(doc);
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                  zonglink.add(linkbo);
                },Throwable->initEmptyView());
    }
    public static void launch(Activity activity,String link) {
        Intent mIntent = new Intent(activity,NewshanjuActivity.class);
        mIntent.putExtra(ConstantUtil.EXTRA_URL, link);
        activity.startActivity(mIntent);
    }
    private void initEmptyView() {

    }

    @Override
    public void finishTask() {
        zonglink=new ArrayList<>();
        for(int i=0;i<mNewshanjuInfo.getjishu().size();i++){
            linkji=mNewshanjuInfo.getjishu().get(i).getLink();
            loadData();
        }
        mNewshanjuInfo.setZonglink(zonglink);
        mHanjujishuAdapter.sethanjujishuInfo(mNewshanjuInfo);
        mHanjujishuAdapter .notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }

    @Override
    public void initToolBar() {

    }

}
