package com.panpan.hantaimi.module.home.hanjuzong;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.panpan.hantaimi.adapter.HanjuleiAdapter;
import com.panpan.hantaimi.base.RxLazyFragment;
import com.panpan.hantaimi.biz.tvbiz;
import com.panpan.hantaimi.entity.hanjuzong.HanjuleiInfo;
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

public class HanjuleiFragment extends RxLazyFragment {
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
    public static final String NEWS_TYPE = "NEWS_TYPE";
    private HanjuleiAdapter mHanjuleiAdapter ;
   private int xuan1;
    private int daixuanye=1;
    private int shixuanye=1;
    private int dianyingye=1;
    private String qlink;
    public static HanjuleiFragment newInstance(int xuan) {
        Bundle args = new Bundle();
        args.putInt(NEWS_TYPE, xuan);
        HanjuleiFragment fragment = new  HanjuleiFragment();
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_live;
    }

    @Override
    public void finishCreateView(Bundle state) {
        xuan1 = getArguments().getInt(NEWS_TYPE);
        isPrepared = true;
        lazyLoad();
        mshang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xuan1==1) {
                    if (daixuanye > 1)
                        daixuanye--;
                    qlink="hanju/list_8_" + Integer.toString(daixuanye) + ".html";

                }
                else if(xuan1==2){
                    if (shixuanye > 1)
                        shixuanye--;
                    qlink ="hanju/list_77_" + Integer.toString(shixuanye) + ".html";

                }
                else if(xuan1==3){
                    if (dianyingye> 1)
                        dianyingye--;
                        qlink ="hanju/list_75_" + Integer.toString(dianyingye) + ".html";

                }
                loadData();
            }
        });
        mxia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xuan1==1) {
                    if (daixuanye < 50)
                        daixuanye++;
                    qlink ="hanju/list_8_" + Integer.toString(daixuanye) + ".html";

                }
                else if(xuan1==2)
                {
                    if (shixuanye < 10)
                        shixuanye++;
                    qlink ="hanju/list_77_" + Integer.toString(shixuanye) + ".html";

                }
                 else if(xuan1==3)
                {
                    if (dianyingye < 16)
                        dianyingye++;
                    qlink ="hanju/list_75_" + Integer.toString(dianyingye) + ".html";

                }
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
        mHanjuleiAdapter  = new HanjuleiAdapter (getActivity());
        mRecyclerView.setAdapter(mHanjuleiAdapter );
        GridLayoutManager layout = new GridLayoutManager(getActivity(), 12);
        layout.setOrientation(LinearLayoutManager.VERTICAL);
        layout.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mHanjuleiAdapter .getSpanSize();
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
            if(xuan1==1)
                qlink="hanju/list_8_1.html";
            else if(xuan1==2)
                qlink ="hanju/list_77_1.html";
            else if(xuan1==3)
                qlink ="hanju/list_75_1.html";
            loadData();
        });
    }

    @Override
    protected void loadData() {

        RetrofitHelper.gethanjuAPI()
                .gethanhanjulei(qlink)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBody -> {
                    try {
                        byte[] responseBytes=responseBody.bytes();
                        String responseUrl = new String(responseBytes,"gb2312");
                        Document doc = Jsoup.parse(responseUrl);
                        tvbiz mtvbiz=new tvbiz();
                        HanjuleiInfo mHanjuleiInfo;
                        mHanjuleiInfo=mtvbiz.HanjuNewsItems(doc);
                        mHanjuleiAdapter .sethanjuleiInfo(mHanjuleiInfo);
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
        mHanjuleiAdapter .notifyDataSetChanged();
        mRecyclerView.scrollToPosition(0);
    }
}

