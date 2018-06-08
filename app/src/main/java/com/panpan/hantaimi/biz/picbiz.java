package com.panpan.hantaimi.biz;

import android.util.Log;

import com.panpan.hantaimi.entity.hanjuzong.PicleiInfo;
import com.panpan.hantaimi.utils.CommonException;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class picbiz {
    public PicleiInfo PicleiNewsItems(Document doc)
        throws CommonException {
        PicleiInfo mdongtai=new PicleiInfo();
        PicleiInfo.DataBean mdongtaiInfo=new PicleiInfo.DataBean();
    List<PicleiInfo.DataBean.PicleiBean> dongt=new ArrayList<>();
        PicleiInfo.DataBean.PicleiBean item = null;
    try {
        Elements units1 = doc.select("[class=con1]");
        Elements links=units1.select("a[href]");
        Elements imgs=units1.select("img[src]");
        Log.i( "getNewsItems: 2",Integer.toString( imgs.size()));
        for (int i = 0; i < imgs.size(); i++) {
            item=new PicleiInfo.DataBean.PicleiBean ();
            item.setLink(links.get((i+2)*2).attr("href"));
            item.setTitle( imgs.get(i).attr("alt"));
            String imgLink = imgs.get(i).attr("src");
            item.setImg(imgLink);
            dongt.add(item);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    mdongtaiInfo.setPiclei(dongt);
    mdongtai.setData(mdongtaiInfo);
    return  mdongtai;
}

}


