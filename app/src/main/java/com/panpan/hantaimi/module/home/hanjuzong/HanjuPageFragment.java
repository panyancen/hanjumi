package com.panpan.hantaimi.module.home.hanjuzong;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.panpan.hantaimi.adapter.pager.HanjuPagerAdapter;
import com.panpan.hantaimi.base.RxLazyFragment;
import com.panpan.hantanmi.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/6/3 0003.
 */

public class HanjuPageFragment extends RxLazyFragment {
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTab;


    public static HanjuPageFragment newInstance() {
        return new HanjuPageFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_hanju_pager;
    }

    @Override
    public void finishCreateView(Bundle state) {
        initToolBar();
        initViewPager();
    }

    private void initToolBar() {

    }

    private void initViewPager() {
        HanjuPagerAdapter mHomeAdapter = new HanjuPagerAdapter(getChildFragmentManager(), getApplicationContext());
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mHomeAdapter);
        mSlidingTab.setViewPager(mViewPager);
        mViewPager.setCurrentItem(0);
    }






}

