package com.panpan.hantaimi.biz;

import android.util.Log;

import com.panpan.hantaimi.entity.hanjuzong.NewsdetailInfo;
import com.panpan.hantaimi.entity.hanjuzong.NewshanjuInfo;
import com.panpan.hantaimi.utils.CommonException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class NewshanjuBiz {
    public NewshanjuInfo NewshanjuNewsItems(Document doc)
            throws CommonException {
        NewshanjuInfo item = null;
        try {
            Elements units1 = doc.select("[class=vdetals-l]");
            Elements imgs=units1.select("img[src]");
            Elements units2 = doc.select("[class=vdetals-r]");
            Elements dais=units2.select("[class=last]");
            Elements times=units2.select("[class=date]");
            Elements zhuyans=units2.select("[class=actor]");
            Elements tests=units2.select("[class=plot]");
            Elements units3 = doc.select("[class=list c_1]");
            Elements jishs=units3.select("a[href]");
            Elements units4=doc.select("div[class]");
            item=new  NewshanjuInfo();
            for(int j=0;j<units4.size();j++){
                String mclass=units4.get(j).attr("class");
                if(mclass.indexOf(" vothercon")!=-1) {
                    item.setContent(units4.get(j).toString());
                }
            }
            NewshanjuInfo.jishuBean itemjishu;
            List< NewshanjuInfo.jishuBean> itemjishus=new ArrayList<>();
            item.setImg(imgs.get(0).attr("src"));
            item.setTitle(imgs.get(0).attr("alt"));
            item.setdai(dais.text());
            item.settest(tests.text());
            item.setzhuyan(zhuyans.text());
            item.settime(times.text());
            Log.i( "getNewsItems: hju",Integer.toString(jishs.size()));
            for(int i=0;i<jishs.size();i++){
                itemjishu= new NewshanjuInfo.jishuBean();
                String jishu=jishs.get(i).attr("href");
                String title=jishs.get(i).text();
                Log.i( "getNewsItems: hju",title);
                itemjishu.setLink(jishu);
                itemjishu.setTitle(title);
                itemjishus.add(itemjishu);
            }
            item.setjishu(itemjishus);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  item;
    }
}
