package com.ideamoment.emars.model;

/**
 * 实体基类
 *
 * 默认提供id字段做为主键
 */
public abstract class BaseEntity {

    private long id;          //主键

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
