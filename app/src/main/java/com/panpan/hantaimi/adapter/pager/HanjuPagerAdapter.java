package com.panpan.hantaimi.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.panpan.hantaimi.module.home.hanjuzong.DongtaiFragment;
import com.panpan.hantaimi.module.home.hanjuzong.HanjuleiFragment;
import com.panpan.hantaimi.module.home.hanjuzong.PicFragment;
import com.panpan.hantaimi.module.home.hanjuzong.zongheFragment;
import com.panpan.hantanmi.R;

/**
 * Created by Administrator on 2018/6/3 0003.
 */

public class HanjuPagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES;
    private Fragment[] fragments;

    public HanjuPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections1);
        fragments = new Fragment[TITLES.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = HanjuleiFragment.newInstance(1);
                    break;
                case 1:
                    fragments[position] = HanjuleiFragment.newInstance(2);
                    break;
                case 2:
                    fragments[position] = HanjuleiFragment.newInstance(3);
                default:
                    break;
            }
        }
        return fragments[position];
    }


    @Override
    public int getCount() {
        return TITLES.length;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}

