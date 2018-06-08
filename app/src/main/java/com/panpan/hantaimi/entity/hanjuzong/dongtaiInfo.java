package com.panpan.hantaimi.entity.hanjuzong;

import java.util.List;

/**
 * Created by Administrator on 2018/5/24 0024.
 */

public class dongtaiInfo {
    private DataBean data;
    public DataBean getData() {
        return data;
    }
    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private List<DongtaiBean> dongtai;

        public List<DongtaiBean> getDongtai() {
            return dongtai;
        }
        public void setDongtai(List<DongtaiBean>dongtai) {
            this.dongtai = dongtai;
        }
        public int getDongtaicount() {
            return dongtai.size();
        }

        public static class DongtaiBean {
            private String title;
            private String img;
            private String link;
            private String data;
            private String jiansu;

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

            public String getdata() {
                return data;
            }

            public void setdata(String data) {
                this.data = data;
            }

            public String getjiansu() {
                return jiansu;
            }

            public void setjiansu(String jiansu) {
                this.jiansu = jiansu;
            }
        }
    }
}
