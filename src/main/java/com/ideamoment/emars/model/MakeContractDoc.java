package com.ideamoment.emars.model;

import com.ideamoment.emars.model.enumeration.ContractDocType;

import java.util.Date;

/**
 * Created by yukiwang on 2018/3/11.
 */
public class MakeContractDoc extends HistoriableEntity {

    private long mcProductId;
    private String name;
    private String path;
    private String desc;
    private String type;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getMcProductId() {
        return mcProductId;
    }

    public void setMcProductId(long mcProductId) {
        this.mcProductId = mcProductId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
