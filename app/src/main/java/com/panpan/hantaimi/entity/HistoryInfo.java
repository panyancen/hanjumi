package com.panpan.hantaimi.entity;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2018/6/2 0002.
 */

public class HistoryInfo extends DataSupport {
    private int id;
    private String tvid;
    private String jishuid;
    private String seetime;

    public String getSeetime() {
        return seetime;
    }

    public void setSeetime(String seetime) {
        this.seetime = seetime;
    }

    public String getJishuid() {

        return jishuid;
    }

    public void setJishuid(String jishuid) {
        this.jishuid = jishuid;
    }

    public String getTvid() {

        return tvid;
    }

    public void setTvid(String tvid) {
        this.tvid = tvid;
    }

    public void setId(int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }
}
