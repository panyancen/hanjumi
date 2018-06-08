package com.panpan.hantaimi.module.home.hanjuzong;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.panpan.hantaimi.adapter.zongheappAdaper;
import com.panpan.hantaimi.base.RxLazyFragment;
import com.panpan.hantaimi.biz.zonghebiz;
import com.panpan.hantaimi.entity.hanjuzong.zongheInfo;
import com.panpan.hantaimi.network.RetrofitHelper;
import com.panpan.hantaimi.utils.CommonException;
import com.panpan.hantaimi.widget.CustomEmptyView;
import com.panpan.hantanmi.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/21 0021.
 */

public class zongheFragment extends RxLazyFragment {
    @BindView(R.id.recycle_zonghe)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout_zonghe)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_layout_zonghe)
    CustomEmptyView mCustomEmptyView;

    private zongheappAdaper mzongheappAdaper;

    public static zongheFragment newInstance() {
        return new zongheFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_zhonghe;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }

    @Override
    protected void initRecyclerView() {
        mzongheappAdaper = new zongheappAdaper(getActivity());
        mRecyclerView.setAdapter(mzongheappAdaper);
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 12);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mzongheappAdaper.getSpanSize(position);
            }
        });
        mRecyclerView.setLayoutManager(layout);
    }


    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(this::loadData);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
    }

    @Override
    protected void loadData() {
        RetrofitHelper.gethanjuAPI()
                .gethanju()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        byte[] responseBytes=responseBody.bytes();
                        String responseUrl = new String(responseBytes,"gb2312");
                        Document doc = Jsoup.parse(responseUrl);
                        zonghebiz mzonghebiz=new zonghebiz();
                        zongheInfo mzongheInfo= new zongheInfo();
                        mzongheInfo=mzonghebiz.zgetNewsItems(doc);
                        mzongheappAdaper.setLiveInfo(mzongheInfo);
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


    private void initEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");

    }


    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    protected void finishTask() {
        hideEmptyView();
        mSwipeRefreshLayout.setRefreshing(false);
        mzongheappAdaper.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }
}
