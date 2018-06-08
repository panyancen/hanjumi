package com.panpan.hantaimi.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.panpan.hantaimi.HanjumiApp;
import com.panpan.hantaimi.network.api.HanjuService;
import com.panpan.hantaimi.network.auxiliary.ApiConstants;
import com.panpan.hantaimi.utils.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * Retrofit帮助类
 */
public class RetrofitHelper {
    private static OkHttpClient mOkHttpClient;
private static int anInt = 0;
private static String name="";
    static {
        initOkHttpClient();
    }
    public static HanjuService gethanjuAPI() {
        return createApi(HanjuService.class, ApiConstants.hanju);
    }
    public static HanjuService gethanjuAPI(int i, String nname) {
       anInt=i;name=nname;
        return createApi(HanjuService.class, ApiConstants.hanju);
    }
    public static HanjuService gethanjuAPI(int i) {
        return createApi(HanjuService.class, ApiConstants.hanjum);
    }


    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(clazz);
    }


    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            if (mOkHttpClient == null) {
                synchronized (RetrofitHelper.class) {
                    if (mOkHttpClient == null) {
                        //设置Http缓存
                        Cache cache = new Cache(new File(HanjumiApp.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
                        mOkHttpClient = new OkHttpClient.Builder()
                                .cache(cache)
                                .addInterceptor(interceptor)
                                .addNetworkInterceptor(new CacheInterceptor())
                                .addNetworkInterceptor(new StethoInterceptor())
                                .retryOnConnectionFailure(true)
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(20, TimeUnit.SECONDS)
                                .readTimeout(20, TimeUnit.SECONDS)
                                .build();
                    }
                }

        }




    }




    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(HanjumiApp.getInstance())) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(HanjumiApp.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}
