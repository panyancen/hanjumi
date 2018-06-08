package com.panpan.hantaimi.entity.hanjuzong;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2018/5/28 0028.
 */

public class NewshanjuInfo  implements Parcelable{
    public String img;
    public String title;
    public String dai;
    public String time;
    public String test;
    public String link;
    public String zhuyan;
    public String content;

    public NewshanjuInfo(Parcel in) {
        img = in.readString();
        title = in.readString();
        dai = in.readString();
        time = in.readString();
        test = in.readString();
        link = in.readString();
        zhuyan = in.readString();
        content = in.readString();
        zonglink = in.createStringArrayList();
    }

    public static final Creator<NewshanjuInfo> CREATOR = new Creator<NewshanjuInfo>() {
        @Override
        public NewshanjuInfo createFromParcel(Parcel in) {
            return new NewshanjuInfo(in);
        }

        @Override
        public NewshanjuInfo[] newArray(int size) {
            return new NewshanjuInfo[size];
        }
    };

    public NewshanjuInfo() {

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<jishuBean> zongjishu;

    public List<String> getZonglink() {
        return zonglink;
    }

    public void setZonglink(List<String> zonglink) {
        this.zonglink = zonglink;
    }

    public List<String> zonglink;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time) {
        this.time = time;
    }

    public String getdai() {
        return dai;
    }

    public void setdai(String dai) {
        this.dai = dai;
    }

    public String gettest() {
        return test;
    }

    public void settest(String test) {
        this.test = test;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getzhuyan() {
        return zhuyan;
    }

    public void setzhuyan(String zhuyan) {
        this.zhuyan = zhuyan;
    }

    public List<jishuBean> getjishu() {
        return zongjishu;
    }
    public void setjishu(List<jishuBean>zongjishu) {
        this.zongjishu = zongjishu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(img);
        dest.writeString(title);
        dest.writeString(dai);
        dest.writeString(time);
        dest.writeString(test);
        dest.writeString(link);
        dest.writeString(zhuyan);
        dest.writeString(content);
        dest.writeStringList(zonglink);
    }


    public static class jishuBean {
        private String title;
        private String link;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

    }
}
