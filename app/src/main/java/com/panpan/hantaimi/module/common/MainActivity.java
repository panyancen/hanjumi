package com.panpan.hantaimi.module.common;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.panpan.hantaimi.base.RxBaseActivity;
import com.panpan.hantaimi.entity.HistoryInfo;
import com.panpan.hantaimi.module.entry.HistoryFragment;
import com.panpan.hantaimi.module.entry.SettingFragment;
import com.panpan.hantaimi.module.home.HomePageFragment;
import com.panpan.hantaimi.utils.ConstantUtil;
import com.panpan.hantaimi.utils.PreferenceUtil;
import com.panpan.hantaimi.utils.ToastUtil;
import com.panpan.hantaimi.widget.CircleImageView;
import com.panpan.hantanmi.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by hcc on 16/8/7 14:12
 * 100332338@qq.com
 * <p/>
 * MainActivity
 */
public class MainActivity extends RxBaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.id_tablayout)
     TabLayout mTab;
    @BindView(R.id.id_viewpager)
   ViewPager mViewpager;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    public static boolean isForeground = false;
    private Fragment[] fragments;
    private int index;
    private long exitTime;
    private HomePageFragment mHomePageFragment;
    private FragmentDreamAdapter mAdapter;
    private String[] titles;
    ArrayList<Fragment> fragments1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        PreferenceUtil.putBoolean(ConstantUtil.KEY, true);
        //初始化Fragment
        initFragments();
        //初始化侧滑菜单
        initNavigationView();
        initData();
    }

    private void initData() {

        titles = new String[]{"韩蜜","泰蜜"};
        fragments1=new ArrayList<Fragment>();
        fragments1.add(HomePageFragment.newInstance());
        fragments1.add(HomePageFragment.newInstance());
        mAdapter = new FragmentDreamAdapter(this.getSupportFragmentManager(),fragments1);
        mViewpager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewpager);
    }







    public class FragmentDreamAdapter extends FragmentStatePagerAdapter {
        private ArrayList<Fragment> fragments;

        private FragmentManager fm;
        public FragmentDreamAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fm = fm;
            this.fragments = fragments;
            notifyDataSetChanged();
        }


        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position % titles.length];
        }
    }

    /**
     * 初始化Fragments
     */
    private void initFragments() {
        mHomePageFragment = HomePageFragment.newInstance();
        HistoryFragment mHistoryFragment = HistoryFragment.newInstance();
        SettingFragment mSettingFragment = SettingFragment.newInstance();
        fragments = new Fragment[]{
                mHomePageFragment,
                mHistoryFragment,
                mSettingFragment
        };

    }


    /**
     * 初始化NavigationView
     */
    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
        CircleImageView mUserAvatarView = (CircleImageView) headerView.findViewById(R.id.user_avatar_view);
        TextView mUserName = (TextView) headerView.findViewById(R.id.user_name);
        TextView mUserSign = (TextView) headerView.findViewById(R.id.user_other_info);
        ImageView mSwitchMode = (ImageView) headerView.findViewById(R.id.iv_head_switch_mode);
        //设置头像
        mUserAvatarView.setImageResource(R.drawable.hantaimilogo);

        //设置日夜间模式切换
        mSwitchMode.setOnClickListener(v -> switchNightMode());
        boolean flag = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (flag) {
            mSwitchMode.setImageResource(R.drawable.ic_switch_daily);
        } else {
            mSwitchMode.setImageResource(R.drawable.ic_switch_night);
        }
    }


    /**
     * 日夜间模式切换
     */
    private void switchNightMode() {
        boolean isNight = PreferenceUtil.getBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        if (isNight) {
            // 日间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, false);
        } else {
            // 夜间模式
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            PreferenceUtil.putBoolean(ConstantUtil.SWITCH_MODE_KEY, true);
        }
        recreate();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.item_home:
                // 主页
                changeFragmentIndex(item, 0);
                return true;
            case R.id.item_history:
                // 历史记录
                changeFragmentIndex(item, 1);
                return true;
            case R.id.item_settings:
                // 设置中心
                changeFragmentIndex(item, 2);
                return true;
        }
        return false;
    }


    /**
     * Fragment切换
     */
    private void switchFragment() {
        fragments1.set(0,fragments[index]);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 切换Fragment的下标
     */
    public void changeFragmentIndex(MenuItem item, int currentIndex) {
        index = currentIndex;
        switchFragment();
        item.setChecked(true);
    }


    /**
     * DrawerLayout侧滑菜单开关
     */
    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }


    /**
     * 监听back键处理DrawerLayout和SearchView
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(mDrawerLayout.getChildAt(1))) {
                mDrawerLayout.closeDrawers();
            } else {
                if (mHomePageFragment != null) {
                    try {
                        if (mHomePageFragment.isOpenSearchView()) {
                            mHomePageFragment.closeSearchView();
                        } else {
                            exitApp();
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        exitApp();
                    }
                } else {
                    exitApp();
                }
            }
        }
        return true;
    }


    /**
     * 双击退出App
     */
    private void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.ShortToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            PreferenceUtil.remove(ConstantUtil.SWITCH_MODE_KEY);
            finish();
        }
    }


    /**
     * 解决App重启后导致Fragment重叠的问题
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }


    @Override
    public void initToolBar() {
    }
}
