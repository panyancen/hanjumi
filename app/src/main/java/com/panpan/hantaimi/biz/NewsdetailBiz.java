package com.panpan.hantaimi.biz;

import android.util.Log;

import com.panpan.hantaimi.entity.hanjuzong.NewsdetailInfo;
import com.panpan.hantaimi.utils.CommonException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by Administrator on 2018/5/26 0026.
 */

public class NewsdetailBiz {
    public   NewsdetailInfo NewsdetailNewsItems(Document doc)
            throws CommonException {
        NewsdetailInfo item = null;
        try {
            Elements units1 = doc.select("[class=body]");
            Elements img=units1.select("img[src]");
                item=new  NewsdetailInfo();
                item.setLink(units1.get(0).toString());
                item.setImg(img.get(0).attr("src"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  item;
    }
    public   NewsdetailInfo NewstuNewsItems(Document doc)
            throws CommonException {
        NewsdetailInfo item = null;
        try {
            Elements units1 = doc.select("[class=ui-content]");
            Elements img=units1.select("img[src]");
            item=new  NewsdetailInfo();
            item.setLink(units1.get(0).toString());
            item.setImg(img.get(0).attr("src"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  item;
    }

}
