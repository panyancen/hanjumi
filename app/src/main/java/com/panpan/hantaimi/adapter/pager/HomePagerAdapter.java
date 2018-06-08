package com.panpan.hantaimi.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.panpan.hantaimi.module.home.hanjuzong.DongtaiFragment;
import com.panpan.hantaimi.module.home.hanjuzong.HanjuPageFragment;
import com.panpan.hantaimi.module.home.hanjuzong.HanjuleiFragment;
import com.panpan.hantaimi.module.home.hanjuzong.PicFragment;
import com.panpan.hantaimi.module.home.hanjuzong.zongheFragment;
import com.panpan.hantanmi.R;

/**
 * Created by hcc on 16/8/4 14:12
 * 100332338@qq.com
 * <p/>
 * 主界面Fragment模块Adapter
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

  private final String[] TITLES;
  private Fragment[] fragments;

  public HomePagerAdapter(FragmentManager fm, Context context) {
    super(fm);
    TITLES = context.getResources().getStringArray(R.array.sections);
    fragments = new Fragment[TITLES.length];
  }


  @Override
  public Fragment getItem(int position) {
    if (fragments[position] == null) {
      switch (position) {
        case 0:
          fragments[position] = zongheFragment.newInstance();
          break;
        case 1:
          fragments[position] = DongtaiFragment.newInstance(1);
          break;
        case 2:
          fragments[position] = HanjuPageFragment.newInstance();
          break;
        case 3:
          fragments[position] = DongtaiFragment.newInstance(2);
          break;
        case 4:
          fragments[position] = PicFragment.newInstance();
          break;
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
