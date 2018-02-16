package com.ideamoment.emars.model;

import com.ideamoment.emars.model.enumeration.ProductType;

/**
 * Created by yukiwang on 2018/2/16.
 */
public class Subject extends HistoriableEntity {

    private String name;
    private String desc;
    private int order;
    private String type = ProductType.TEXT;
    private String ratio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }
}
