package com.panpan.hantaimi.module.entry;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.panpan.hantaimi.base.RxLazyFragment;
import com.panpan.hantaimi.module.common.AppIntroduceActivity;
import com.panpan.hantaimi.module.common.HotBitmapGGInfoActivity;
import com.panpan.hantaimi.module.common.LoginActivity;
import com.panpan.hantaimi.module.common.MainActivity;
import com.panpan.hantaimi.utils.ConstantUtil;
import com.panpan.hantaimi.utils.PreferenceUtil;
import com.panpan.hantanmi.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hcc on 16/8/7 14:12
 * 100332338@qq.com
 * <p/>
 * 设置与帮助
 */
public class SettingFragment extends RxLazyFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_setting;
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle("设置与帮助");
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);
        mToolbar.setNavigationOnClickListener(v -> {
            Activity activity1 = getActivity();
            if (activity1 instanceof MainActivity) {
                ((MainActivity) activity1).toggleDrawer();
            }
        });

    }

    @OnClick({R.id.layout_about_me, R.id.layout_about_app,R.id.btn_logout})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.layout_about_me:
                //关于我
                startActivity(new Intent(getActivity(), HotBitmapGGInfoActivity.class));
                break;
            case R.id.layout_about_app:
                //关于哔哩哔哩
                startActivity(new Intent(getActivity(), AppIntroduceActivity.class));
                break;
            case R.id.btn_logout:
                //退出登录
                getActivity().finish();
                break;
        }
    }

    public String getVersionCode() {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        assert packageInfo != null;
        return packageInfo.versionName;
    }
}
