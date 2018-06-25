package com.ideamoment.emars.dashboard.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by yukiwang on 2018/6/25.
 */
public class Contract implements Comparable<Contract>{

    private long id;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    private String title;
    private String code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int compareTo(Contract o) {
        long x = this.getCreateTime().getTime();
        long y = o.getCreateTime().getTime();
        long z = y - x;
        int i = (int) z;
//        int i = this.getCreateTime().getTime() - o.getCreateTime().getTime();
        return i;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
