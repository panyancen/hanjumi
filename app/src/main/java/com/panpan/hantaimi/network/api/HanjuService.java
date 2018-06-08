package com.panpan.hantaimi.network.api;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2018/5/17 0017.
 */

public interface HanjuService {
    @GET("/")
    Observable<ResponseBody> gethanju();


    @GET("/{id}")
    Observable<ResponseBody> gethanjudongtai(@Path("id") String id);

    @GET("/{id}")
    Observable<ResponseBody> gethanhanjulei(@Path("id") String id);

    @GET("/{tid}")
    Observable<ResponseBody> gethantupian(@Path("tid") String tid);

    @GET("/{id}")
    Observable<ResponseBody> gethantupianid(@Path("id") String id);


    @POST("plus/search.php" )
    Observable<ResponseBody> getsearchid();

}
