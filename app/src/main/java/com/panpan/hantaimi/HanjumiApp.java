package com.panpan.hantaimi;

import android.app.Application;


import com.facebook.stetho.Stetho;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by hcc on 16/8/7 21:18
 * 100332338@qq.com
 * <p/>
 * 哔哩哔哩动画App
 */
public class HanjumiApp extends LitePalApplication {

    public static HanjumiApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        LitePal.initialize(this);
        init();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    private void init() {
        //初始化Leak内存泄露检测工具
      //  LeakCanary.install(this);
        //初始化Stetho调试工具
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    public static HanjumiApp getInstance() {
        return mInstance;
    }

}
