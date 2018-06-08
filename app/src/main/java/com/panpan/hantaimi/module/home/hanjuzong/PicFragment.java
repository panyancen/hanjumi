package com.panpan.hantaimi.module.home.hanjuzong;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.panpan.hantaimi.adapter.PicleiAdapter;
import com.panpan.hantaimi.base.RxLazyFragment;
import com.panpan.hantaimi.biz.picbiz;
import com.panpan.hantaimi.entity.hanjuzong.PicleiInfo;
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
 * Created by Administrator on 2018/5/24 0024.
 */

public class PicFragment extends RxLazyFragment {
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;
    @BindView(R.id.shang_yi_ye)
    Button mshang;
    @BindView(R.id.xia_yi_ye)
    Button mxia;
    private PicleiAdapter mPicleiAdapter ;
    private int shixuanye=1;
    private String qlink;
    public static  PicFragment newInstance() {
        PicFragment fragment = new  PicFragment();
        return  fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_live;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
        mshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (shixuanye > 1)
                        shixuanye--;
                    qlink ="hanjutupian/list_4_" + Integer.toString(shixuanye) + ".html";
                    loadData();

            }
        });
        mxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (shixuanye < 100)
                        shixuanye++;
                    qlink ="hanjutupian/list_4_" + Integer.toString(shixuanye) + ".html";
                    loadData();

            }
        });
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
        mPicleiAdapter  = new PicleiAdapter (getActivity());
        mRecyclerView.setAdapter(mPicleiAdapter );
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 12);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mPicleiAdapter .getSpanSize();
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
            qlink="hanjutupian/list_4_1.html";
            loadData();
        });
    }

    @Override
    protected void loadData() {
            RetrofitHelper.gethanjuAPI()
                    .gethantupian(qlink)
                    .compose(bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseBody -> {
                        try {
                            byte[] responseBytes=responseBody.bytes();
                            String responseUrl = new String(responseBytes,"gb2312");
                            Document doc = Jsoup.parse(responseUrl);
                           picbiz mtvbiz=new picbiz();
                            PicleiInfo mPicleiInfo;
                            mPicleiInfo=mtvbiz.PicleiNewsItems(doc);
                            mPicleiAdapter .setpicleiInfo(mPicleiInfo);
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
        mPicleiAdapter .notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }
}
