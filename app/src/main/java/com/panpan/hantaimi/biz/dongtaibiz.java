package com.panpan.hantaimi.biz;

import android.util.Log;

import com.panpan.hantaimi.entity.hanjuzong.dongtaiInfo;
import com.panpan.hantaimi.utils.CommonException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class dongtaibiz {
    public  dongtaiInfo dongtaiNewsItems(Document doc)
            throws CommonException {
        dongtaiInfo mdongtai=new dongtaiInfo();
      dongtaiInfo.DataBean mdongtaiInfo=new dongtaiInfo.DataBean();
        List<dongtaiInfo.DataBean.DongtaiBean> dongt=new ArrayList<>();
        dongtaiInfo.DataBean.DongtaiBean  item = null;
        try {
            Elements units1 = doc.select("[class=box]");
            Elements links=units1.select("a[href]");
            Elements imgs=units1.select("img[src]");

            for (int i = 0; i < imgs.size(); i++) {
              item=new dongtaiInfo.DataBean.DongtaiBean ();
                item.setLink(links.get((i+1)*2+i).attr("href"));
                item.setTitle( imgs.get(i).attr("alt"));
                String imgLink = imgs.get(i).attr("src");
                String data=imgLink.substring(imgLink.indexOf("http://img2.467835.com/allimg/")+30,36);
                item.setdata(data);
                item.setjiansu(links.get((i+1)*2+i).text());
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

}
