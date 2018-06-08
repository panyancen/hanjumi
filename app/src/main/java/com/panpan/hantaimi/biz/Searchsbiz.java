package com.panpan.hantaimi.biz;

import android.util.Log;

import com.panpan.hantaimi.entity.SearchArchiveInfo;
import com.panpan.hantaimi.entity.hanjuzong.NewsdetailInfo;
import com.panpan.hantaimi.utils.CommonException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class Searchsbiz {
    public List<SearchArchiveInfo> SearchItems(Document doc)
            throws CommonException {
        List<SearchArchiveInfo> mSearchArchiveInfo=new ArrayList<>();
        SearchArchiveInfo item = null;
        try {
            Elements units1 = doc.select("[class=item]");
            Elements units2=units1.select("h3");
            Elements links=units2.select("a[href]");
            Log.i( "getNewsItems: ss",Integer.toString(links.size()));
          for(int i=0;i<units1.size();i++) {
              item = new SearchArchiveInfo();
              String link=links.get(i).attr("href");
              Log.i( "getNewsItems: ss",link);

              item.setLink(link);
              item.settitle(units1.get(i).select("h3").text());
              item.setcontent(units1.get(i).select("[class=intro]").get(0).text());
              item.settime(units1.get(i).select("[class=info]").get(0).text());
              Log.i( "getNewsItems: 1ss",units1.get(i).select("[class=info]").get(0).text());
              int type=5;
              if(link.indexOf("hanju/")!=-1)
                 type=0;
              if(link.indexOf("hanjudongtai")!=-1)
                  type=1;
              else if(link.indexOf("hanjutupian")!=-1)
                  type=2;
              item.setType(type);
              if(type!=5)
                 mSearchArchiveInfo.add(item);}

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  mSearchArchiveInfo;
    }
}
