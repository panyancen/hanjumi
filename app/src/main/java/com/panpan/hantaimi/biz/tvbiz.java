package com.panpan.hantaimi.biz;

import android.util.Log;

import com.panpan.hantaimi.entity.hanjuzong.HanjuleiInfo;
import com.panpan.hantaimi.utils.CommonException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class tvbiz {
    public HanjuleiInfo HanjuNewsItems(Document doc)
            throws CommonException {
        HanjuleiInfo mdongtai=new HanjuleiInfo();
        HanjuleiInfo.DataBean mdongtaiInfo=new HanjuleiInfo.DataBean();
        List<HanjuleiInfo.DataBean.DongtaiBean> dongt=new ArrayList<>();
        HanjuleiInfo.DataBean.DongtaiBean  item = null;
        try {
            Elements units1 = doc.select("[style=z-index:1]");
            Elements links=units1.select("a[href]");
            Elements imgs=units1.select("img[src]");
            Elements datas=doc.select("[class=desc]");
            Elements zhuyans=units1.select("[class=actor]");

            for (int i = 0; i < imgs.size(); i++) {
                item=new HanjuleiInfo.DataBean.DongtaiBean ();
                item.setLink(links.get((i+1)*2+i).attr("href"));
                item.setTitle( imgs.get(i).attr("alt"));
                String imgLink = imgs.get(i).attr("src");
                item.setdata(datas.get(i*2).text());
                item.setzhuyan(zhuyans.get(i*2).text());
                item.setImg(imgLink);
                dongt.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mdongtaiInfo.setDongtai(dongt);
        mdongtai.setData(mdongtaiInfo);
        return  mdongtai;
    }
public String getmediaplayerInfo(Document doc){
      String link="";
      Elements e = doc.getElementsByTag("script");
      String[] data = e.get(7).data().toString().split("var");
      for(int i=0;i<data.length;i++)
      {
          if(data[i].indexOf("vid=")!=-1) {
              link = data[i].substring(data[i].indexOf("vid='") + 5, data[i].indexOf("';"));
              Log.i("getNewsItems: link", link);
          }
      }
        return link;
}
}
