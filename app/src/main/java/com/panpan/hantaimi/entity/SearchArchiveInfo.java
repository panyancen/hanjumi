package com.panpan.hantaimi.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class SearchArchiveInfo {
            private String title;
            private String time;
            private String content;
            private int type;
            private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String gettitle() {
                return title;
            }

            public void settitle(String title) {
                this.title = title;
            }

            public String gettime() {
                return time;
            }

            public void settime(String time) {
                this.time = time;
            }

            public String getcontent() {
                return content;
            }

            public void setcontent(String content) {
                this.content = content;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }


}
