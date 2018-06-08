package com.panpan.hantaimi.module.entry;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.panpan.hantaimi.adapter.DongtaiAdapter;
import com.panpan.hantaimi.adapter.HanjuleiAdapter;
import com.panpan.hantaimi.adapter.HistoryAdapter;
import com.panpan.hantaimi.base.RxLazyFragment;
import com.panpan.hantaimi.biz.NewshanjuBiz;
import com.panpan.hantaimi.biz.tvbiz;
import com.panpan.hantaimi.entity.HistoryInfo;
import com.panpan.hantaimi.entity.HistoryxianInfo;
import com.panpan.hantaimi.entity.hanjuzong.HanjuleiInfo;
import com.panpan.hantaimi.entity.hanjuzong.NewshanjuInfo;
import com.panpan.hantaimi.module.common.MainActivity;
import com.panpan.hantaimi.network.RetrofitHelper;
import com.panpan.hantaimi.utils.CommonException;
import com.panpan.hantaimi.widget.CustomEmptyView;
import com.panpan.hantanmi.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/8/7 14:12
 * 100332338@qq.com
 * <p/>
 * 历史记录
 */
public class HistoryFragment extends RxLazyFragment {
    @BindView(R.id.empty_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    CustomEmptyView mCustomEmptyView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private HistoryAdapter mHistoryAdapter;
    private String qlink;
    private HistoryInfo mHistoryInfoi;
    private String jishuid;
    private String seetime;
    private int i=0;
    List<HistoryxianInfo> mHistoryxianInfos;
    List<HistoryInfo> mHistoryInfos;
    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }


    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle("历史记录");
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);
        mToolbar.setNavigationOnClickListener(v -> {
            Activity activity1 = getActivity();
            if (activity1 instanceof MainActivity) {
                ((MainActivity) activity1).toggleDrawer();
            }
        });
        initRecyclerView();
        loadall();
    }
    @Override
    protected void initRecyclerView() {
        mHistoryAdapter = new HistoryAdapter(getActivity());
        mRecyclerView.setAdapter(mHistoryAdapter);
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 12);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mHistoryAdapter.getSpanSize();
            }
        });
        mRecyclerView.setLayoutManager(layout);
    }
    public void loadall(){
        mHistoryxianInfos=new ArrayList<>();
           mHistoryInfos = DataSupport.findAll(HistoryInfo.class);

        Log.i( "mHistoryInfos",Integer.toString( mHistoryInfos.size()));
        if(mHistoryInfos.size()==0)
            initNOView();
        else {

            for (HistoryInfo mHistoryInfo : mHistoryInfos) {
                HistoryxianInfo mHistoryxianInfo=new HistoryxianInfo();
                mHistoryxianInfo.setjishuid(mHistoryInfo.getJishuid());
                mHistoryxianInfo.setLink(mHistoryInfo.getTvid());
                mHistoryxianInfo.setseetime(mHistoryInfo.getSeetime());
                mHistoryxianInfo.setId(Integer.toString(mHistoryInfo.getId()));
                mHistoryxianInfos.add(mHistoryxianInfo);
                qlink = mHistoryInfo.getTvid();
                jishuid = mHistoryInfo.getJishuid();
                seetime = mHistoryInfo.getSeetime();
                loadData();
            }
        }

    }
    protected void loadData() {
        RetrofitHelper.gethanjuAPI()
                .gethantupianid(qlink)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        byte[] responseBytes=responseBody.bytes();
                        String responseUrl = new String(responseBytes,"gb2312");
                        Document doc = Jsoup.parse(responseUrl);
                        NewshanjuBiz mNewshanjuBiz=new NewshanjuBiz();
                        NewshanjuInfo mNewshanjuInfo;
                        mNewshanjuInfo=mNewshanjuBiz.NewshanjuNewsItems(doc);
                        mHistoryxianInfos.get(i).setdai(mNewshanjuInfo.getdai());
                        mHistoryxianInfos.get(i).setImg(mNewshanjuInfo.getImg());
                        mHistoryxianInfos.get(i).setTitle(mNewshanjuInfo.getTitle());
                        i++;
                        mHistoryAdapter.setHistoryInfo(mHistoryxianInfos);
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } catch (CommonException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    finishTask();
                },Throwable->initEmptyView());

    }
    @Override
    public void finishTask() {
        if(i==mHistoryInfos.size())
            i=0;
         mHistoryAdapter.notifyDataSetChanged();
         mRecyclerView.scrollToPosition(0);

    }
    private void initEmptyView() {
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.ic_movie_pay_order_error);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");

    }
    private void initNOView(){
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("暂时还没有观看记录哟");
    }
}
