package com.panpan.hantaimi.biz;

import android.util.Log;

import com.panpan.hantaimi.entity.hanjuzong.zongheInfo;
import com.panpan.hantaimi.utils.CommonException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class zonghebiz {
    public zongheInfo zgetNewsItems(Document doc)
            throws CommonException {
        zongheInfo zongheInfo=new zongheInfo();
        zongheInfo.DataBean newsItems = new zongheInfo.DataBean();
        List<zongheInfo.DataBean.BannerBean> banner =new ArrayList<>();
        List<zongheInfo.DataBean.zhouxingBean> zhouxing=new ArrayList<>();
        List<zongheInfo.DataBean.picBean> pic=new ArrayList<>();
        List<zongheInfo.DataBean.dongtBean> dongt=new ArrayList<>();
        List<zongheInfo.DataBean.gengxtvsBean> gengxtvs=new ArrayList<>();

        banner=getbanner(doc);
        zhouxing=getzhouxing(doc);
        pic=getpic(doc);
        dongt=getdongt(doc);
        gengxtvs=getgengxtvs(doc);
        newsItems.setBanner(banner);
        newsItems.setdongt(dongt);
        newsItems.setpic(pic);
        newsItems.setgengxtv(gengxtvs);
        newsItems.setzhouxing(zhouxing);
        zongheInfo.setData(newsItems);
        return  zongheInfo;
    }


    public  List<zongheInfo.DataBean.BannerBean> getbanner(Document doc) {
        List<zongheInfo.DataBean.BannerBean> banner=new ArrayList<>();
        zongheInfo.DataBean.BannerBean item = null;
        try {
            Elements units1 = doc.select("[class=slide-pic]");
            Elements links=units1.select("a[href]");
            Elements imgs=units1.select("img[src]");
            Log.i( "getNewsItems: 1",Integer.toString( links.size()));
            for (int i = 0; i < links.size(); i++) {
                item = new zongheInfo.DataBean.BannerBean();
                item.setLink(links.get(i).attr("href"));
                item.setTitle(links.get(i).attr("title"));
                String imgLink = imgs.get(i).attr("src");
                item.setImg(imgLink);
                banner.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return banner;
    }
    public  List<zongheInfo.DataBean.zhouxingBean> getzhouxing(Document doc) {
        List<zongheInfo.DataBean.zhouxingBean> zhouxing=new ArrayList<>();
        zongheInfo.DataBean.zhouxingBean item = null;
        try {
            Elements units1 = doc.select("[class=blk09]");
            Elements links=units1.select("a[href]");
            Elements imgs=units1.select("img[src]");
            Log.i( "getNewsItems: 2",Integer.toString( links.size()));
            for (int i = 0; i < links.size(); i++) {
                item = new zongheInfo.DataBean.zhouxingBean();
                item.setLink(links.get(i).attr("href"));
                item.setTitle( imgs.get(i).attr("alt"));
                String imgLink = imgs.get(i).attr("src");
                item.setImg(imgLink);
                zhouxing.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return zhouxing;
    }

    public  List<zongheInfo.DataBean.dongtBean> getdongt(Document doc) {
        List<zongheInfo.DataBean.dongtBean> dongt=new ArrayList<>();
        zongheInfo.DataBean.dongtBean item = null;
        try {
            Elements units1 = doc.select("[class=blk12 clearfix]");
            Elements units2=units1.select("[class=left clearfix]");
            Elements links=units2.select("a[href]");
            Elements imgs=units2.select("img[src]");
            Log.i( "getNewsItems: 5",Integer.toString( links.size()));
            for (int i = 0; i < imgs.size(); i++) {
                item = new zongheInfo.DataBean.dongtBean();
                item.setLink(links.get(i*2).attr("href"));
                item.setTitle(imgs.get(i).attr("alt"));
                String imgLink = imgs.get(i).attr("src");
                item.setImg(imgLink);
                dongt.add(item);
            }

            Elements linkss=units1.select("[class=link0]");
            Log.i( "getNewsItems: 5",Integer.toString(  linkss.size()));
            for (int i = 0; i < linkss.size(); i++) {
                item = new zongheInfo.DataBean.dongtBean();
                item.setLink(linkss.get(i).attr("href"));
                item.setTitle(linkss.get(i).text());
                dongt.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dongt;
    }

    public  List<zongheInfo.DataBean.picBean> getpic(Document doc) {
        List<zongheInfo.DataBean.picBean> pic=new ArrayList<>();
        zongheInfo.DataBean.picBean item = null;
        try {
            Elements units1 = doc.select("[class=con]");
            Elements links=units1.select("a[href]");
            Elements imgs=units1.select("img[src]");
            Log.i( "getNewsItems: 3",Integer.toString( links.size()));
            for (int i = 0; i < links.size(); i++) {
                item = new zongheInfo.DataBean.picBean();
                item.setLink(links.get(i).attr("href"));
                item.setTitle(imgs.get(i).attr("alt"));
                String imgLink = imgs.get(i).attr("src");
                item.setImg(imgLink);
                pic.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pic;
    }

    public  List<zongheInfo.DataBean.gengxtvsBean> getgengxtvs(Document doc) {
        List<zongheInfo.DataBean.gengxtvsBean> gengxtvs=new ArrayList<>();
        zongheInfo.DataBean.gengxtvsBean item = null;
        try {
            Elements units1 = doc.select("[class=m-ddone]");
            Elements links=units1.select("a[href]");
            Elements imgs=units1.select("img[src]");
            Log.i( "getNewsItems: 4",Integer.toString( links.size()));
            for (int i = 0; i < imgs.size(); i++) {
                item = new zongheInfo.DataBean.gengxtvsBean();
                item.setLink(links.get(i*2).attr("href"));
                item.settit(links.get(i*2).text());
                item.setTitle(imgs.get(i).attr("alt"));
                String imgLink = imgs.get(i).attr("src");
                item.setImg(imgLink);
                gengxtvs.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gengxtvs;
    }


}
