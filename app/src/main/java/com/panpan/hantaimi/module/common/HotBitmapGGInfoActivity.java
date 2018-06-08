package com.panpan.hantaimi.module.common;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.panpan.hantaimi.base.RxBaseActivity;
import com.panpan.hantanmi.R;

import butterknife.BindView;

/**
 * Created by hcc on 16/8/7 14:12
 * 100332338@qq.com
 * <p/>
 * 关于我
 */
public class HotBitmapGGInfoActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_hotbitmapgg;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("关于我");

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
