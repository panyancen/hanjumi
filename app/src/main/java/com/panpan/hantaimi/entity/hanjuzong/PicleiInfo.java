package com.panpan.hantaimi.entity.hanjuzong;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class PicleiInfo {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private List<PicleiBean> Piclei;

        public List<PicleiBean> getDongtai() {
            return Piclei;
        }

        public void setPiclei(List<PicleiBean> Piclei) {
            this.Piclei = Piclei;
        }

        public int getPicleicount() {
            return Piclei.size();
        }

        public static class PicleiBean {
            private String title;
            private String img;
            private String link;
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

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

        }
    }
}

