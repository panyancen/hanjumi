package com.panpan.hantaimi.module.saerch;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.panpan.hantaimi.adapter.HanjuleiAdapter;
import com.panpan.hantaimi.adapter.SearchleiAdapter;
import com.panpan.hantaimi.base.RxLazyFragment;
import com.panpan.hantaimi.biz.Searchsbiz;
import com.panpan.hantaimi.biz.tvbiz;
import com.panpan.hantaimi.entity.SearchArchiveInfo;
import com.panpan.hantaimi.entity.hanjuzong.HanjuleiInfo;
import com.panpan.hantaimi.module.home.hanjuzong.HanjuleiFragment;
import com.panpan.hantaimi.network.RetrofitHelper;
import com.panpan.hantaimi.network.RetrofitHelperma;
import com.panpan.hantaimi.utils.CommonException;
import com.panpan.hantaimi.widget.CustomEmptyView;
import com.panpan.hantanmi.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/6/4 0004.
 */

public class SearchleiFragment extends RxLazyFragment {
    @BindView(R.id.search_recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.search_layout)
    CustomEmptyView mCustomEmptyView;

    public static final String NEWS_TYPE = "NEWS_TYPE";
    public static final String NEWS_NAME = "NEWS_NAME";
    private SearchleiAdapter mSearchleiAdapter;
    private int xuan1=0;
    private String name="";
    private int guan=0;
    public static  SearchleiFragment newInstance(String name,int xuan) {
        Bundle args = new Bundle();
        args.putInt(NEWS_TYPE, xuan);
        args.putString(NEWS_NAME , name);
        SearchleiFragment fragment = new   SearchleiFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_search;
    }

    @Override
    public void finishCreateView(Bundle state) {
        xuan1 = getArguments().getInt(NEWS_TYPE);
        name=getArguments().getString(NEWS_NAME);
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
        mSearchleiAdapter  = new SearchleiAdapter (getActivity());
        mRecyclerView.setAdapter(mSearchleiAdapter);
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 12);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mSearchleiAdapter .getSpanSize();
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
    protected void loadData()  {
        RetrofitHelperma.gethanjuAPI(xuan1
                ,name)
               .getsearchid()
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        byte[] responseBytes=responseBody.bytes();
                        String responseUrl = new String(responseBytes,"gb2312");
                        Document doc = Jsoup.parse(responseUrl);
                        Searchsbiz mSearchsbiz=new Searchsbiz();
                        List<SearchArchiveInfo> mSearchArchiveInfo;
                        mSearchArchiveInfo=mSearchsbiz.SearchItems(doc);
                        mSearchleiAdapter .setSearchArchiveInfo(mSearchArchiveInfo);
                        guan=0;
                       if(mSearchArchiveInfo.size()==0)
                           guan=1;
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } catch (CommonException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(guan==0)
                    finishTask();
                    else
                        noEmptyView();
                },Throwable->initEmptyView());

    }


    private void initEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");

    }
    private void noEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("没有数据~(≧▽≦)~啦啦啦.");

    }

    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void finishTask() {
        hideEmptyView();
        mSwipeRefreshLayout.setRefreshing(false);
        mSearchleiAdapter .notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }
}
