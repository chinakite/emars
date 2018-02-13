package com.ideamoment.emars.model;

/**
 * 实体基类
 *
 * 默认提供id字段做为主键
 */
public abstract class BaseEntity {

    private String id;          //主键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
