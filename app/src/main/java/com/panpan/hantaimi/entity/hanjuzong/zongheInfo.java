package com.panpan.hantaimi.entity.hanjuzong;

import java.util.List;

/**
 * Created by Administrator on 2018/5/19 0019.
 */

public class zongheInfo {
    private DataBean data;
    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }
    public static class DataBean {
        private List<BannerBean> banner;
        private List<gengxtvsBean> gengxtvs;
        private List<zhouxingBean> zhouxing;
        private List<dongtBean> dongt;
        private List<picBean> pic;

        public List<BannerBean> getBanner() {
            return banner;
        }
        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }
        public int getBannercount() {
            return banner.size();
        }


        public List<gengxtvsBean> getgengxtv() {
            return gengxtvs;
        }
        public void setgengxtv(List<gengxtvsBean> gengxtvs) {
            this.gengxtvs =gengxtvs;
        }
        public int getgengxtvcount() {
            return gengxtvs.size();
        }

        public List<zhouxingBean> getzhouxing() {
            return zhouxing;
        }
        public void setzhouxing(List<zhouxingBean> zhouxing) {
            this.zhouxing = zhouxing;
        }
        public int getzhouxingcount() {return zhouxing.size();}

        public List<dongtBean> getdongt() {
            return dongt;
        }
        public void setdongt(List<dongtBean> dongt) {
            this.dongt = dongt;
        }
        public int getdongtcount() {
            return dongt.size();
        }

        public List<picBean> getpic() {
            return pic;
        }
        public void setpic(List<picBean> pic) {
            this.pic = pic;
        }
        public int getpiccount() {
            return pic.size();
        }


        public static class BannerBean {
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

        public static class gengxtvsBean {
            private String title;
            private String img;
            private String link;
            private String tit;
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

            public String gettit() {
                return tit;
            }

            public void settit(String tit) {
                this.tit = tit;
            }
        }
        public static class zhouxingBean {
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
        public static class dongtBean {
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
        public static class picBean {
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



