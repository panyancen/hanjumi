package com.panpan.hantaimi.module.saerch;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.panpan.hantaimi.base.RxBaseActivity;
import com.panpan.hantaimi.entity.SearchArchiveInfo;
import com.panpan.hantaimi.module.home.hanjuzong.HanjuleiFragment;
import com.panpan.hantaimi.module.home.hanjuzong.NewsDetailActivity;
import com.panpan.hantaimi.utils.ConstantUtil;
import com.panpan.hantaimi.utils.KeyBoardUtil;
import com.panpan.hantaimi.utils.ShareUtil;
import com.panpan.hantaimi.utils.StatusBarUtil;
import com.panpan.hantaimi.widget.NoScrollViewPager;
import com.panpan.hantanmi.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;


public class TotalStationSearchActivity extends RxBaseActivity {
    @BindView(R.id.sliding_tabs1)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager1)
    NoScrollViewPager mViewPager;
    @BindView(R.id.iv_search_loading1)
    ImageView mLoadingView;
    @BindView(R.id.search_img1)
    ImageView mSearchBtn;
    @BindView(R.id.search_edit1)
    EditText mSearchEdit;
    @BindView(R.id.search_text_clear1)
    ImageView mSearchTextClear;
    @BindView(R.id.search_layout1)
    LinearLayout mSearchLayout;

    private String content;
    private AnimationDrawable mAnimationDrawable;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initToolBar() {
        //设置6.0以上StatusBar字体颜色
        StatusBarUtil.from(this).setLightStatusBar(true).process();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            content = intent.getStringExtra(ConstantUtil.EXTRA_CONTENT);
        }
        mLoadingView.setImageResource(R.drawable.anim_search_loading);
        mAnimationDrawable = (AnimationDrawable) mLoadingView.getDrawable();
        showSearchAnim();
        mSearchEdit.clearFocus();
        mSearchEdit.setText(content);
        getSearchData();
        search();
        setUpEditText();
    }
    @OnClick({ R.id.search_back1, R.id.search_text_clear1})
    public void sayHi(ImageView mImageView) {
        switch (mImageView.getId()) {
            case R.id.search_back1:
                finish();
                break;
            case R.id.search_text_clear1:
                clearData();
                break;
            default:
                break;
        }
    }
    private void setUpEditText() {
        RxTextView.textChanges(mSearchEdit)
                .compose(this.bindToLifecycle())
                .map(CharSequence::toString)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    if (!TextUtils.isEmpty(s)) {
                        mSearchTextClear.setVisibility(View.VISIBLE);
                    } else {
                        mSearchTextClear.setVisibility(View.GONE);
                    }
                });
        RxView.clicks(mSearchTextClear)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aVoid -> mSearchEdit.setText(""));

        RxTextView.editorActions(mSearchEdit)
                .filter(integer -> !TextUtils.isEmpty(mSearchEdit.getText().toString().trim()))
                .filter(integer -> integer == EditorInfo.IME_ACTION_SEARCH)
                .flatMap(new Func1<Integer, Observable<String>>() {
                    @Override
                    public Observable<String> call(Integer integer) {
                        return Observable.just(mSearchEdit.getText().toString().trim());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KeyBoardUtil.closeKeybord(mSearchEdit, TotalStationSearchActivity.this);
                    showSearchAnim();
                    clearData();
                    content = s;
                    getSearchData();
                });
    }


    private void search() {
        RxView.clicks(mSearchBtn)
                .throttleFirst(2, TimeUnit.SECONDS)
                .map(aVoid -> mSearchEdit.getText().toString().trim())
                .filter(s -> !TextUtils.isEmpty(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    KeyBoardUtil.closeKeybord(mSearchEdit, TotalStationSearchActivity.this);
                    showSearchAnim();
                    clearData();
                    content = s;
                    getSearchData();
                });
    }

    private void clearData() {
        titles.clear();
        fragments.clear();
    }

    private void getSearchData() {
        finishTask();
    }


    @Override
    public void finishTask() {
        hideSearchAnim();
        titles.add("综合");
        titles.add("影视");
        titles.add("动态");
        titles.add("图库");

        SearchleiFragment archiveResultsFragment =SearchleiFragment.newInstance(content,0);
        SearchleiFragment archiveResultsFragment1 =SearchleiFragment.newInstance(content,4);
        SearchleiFragment archiveResultsFragment2 =SearchleiFragment.newInstance(content,1);
        SearchleiFragment archiveResultsFragment3 =SearchleiFragment.newInstance(content,2);
        fragments.add(archiveResultsFragment);
        fragments.add(archiveResultsFragment1);
        fragments.add(archiveResultsFragment2);
        fragments.add(archiveResultsFragment3);
        SearchTabAdapter mAdapter = new SearchTabAdapter(getSupportFragmentManager(), titles, fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(titles.size());
        mSlidingTabLayout.setViewPager(mViewPager);
        measureTabLayoutTextWidth(0);
        mSlidingTabLayout.setCurrentTab(0);
        mAdapter.notifyDataSetChanged();
        mSlidingTabLayout.notifyDataSetChanged();
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                measureTabLayoutTextWidth(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    public String checkNumResults(int numResult) {
        return numResult > 100 ? "99+" : String.valueOf(numResult);
    }

    private void measureTabLayoutTextWidth(int position) {
        String title = titles.get(position);
        TextView titleView = mSlidingTabLayout.getTitleView(position);
        TextPaint paint = titleView.getPaint();
        float textWidth = paint.measureText(title);
        mSlidingTabLayout.setIndicatorWidth(textWidth / 3);
    }

    private void showSearchAnim() {
        mLoadingView.setVisibility(View.VISIBLE);
        mSearchLayout.setVisibility(View.GONE);
        mAnimationDrawable.start();
    }


    private void hideSearchAnim() {
        mLoadingView.setVisibility(View.GONE);
        mSearchLayout.setVisibility(View.VISIBLE);
        mAnimationDrawable.stop();
    }

    public void setEmptyLayout() {
        mLoadingView.setVisibility(View.VISIBLE);
        mSearchLayout.setVisibility(View.GONE);
        mLoadingView.setImageResource(R.drawable.search_failed);
    }

    @OnClick(R.id.search_back1)
    void OnBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (mAnimationDrawable != null && mAnimationDrawable.isRunning()) {
            mAnimationDrawable.stop();
            mAnimationDrawable = null;
        }
        super.onBackPressed();
    }


    public static void launch(Activity activity, String str) {
        Intent mIntent = new Intent(activity, TotalStationSearchActivity.class);
        mIntent.putExtra(ConstantUtil.EXTRA_CONTENT, str);
        activity.startActivity(mIntent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimationDrawable != null) {
            mAnimationDrawable.stop();
            mAnimationDrawable = null;
        }
    }


    private static class SearchTabAdapter extends FragmentStatePagerAdapter {
        private List<String> titles;
        private List<Fragment> fragments;

        SearchTabAdapter(FragmentManager fm, List<String> titles, List<Fragment> fragments) {
            super(fm);
            this.titles = titles;
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
