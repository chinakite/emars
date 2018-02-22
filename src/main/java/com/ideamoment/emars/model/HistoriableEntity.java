package com.ideamoment.emars.model;

import com.fasterxml.jackson.databind.ser.Serializers;

import java.util.Date;

/**
 * 定义改动历史字段
 *
 * creator    - 创建人，不可空
 * createTime - 创建时间，不可空
 * modifier   - 修改人，可空
 * modifyTime - 修改时间，可空
 */
public abstract class HistoriableEntity extends BaseEntity {

    protected Long creator;       //创建人

    protected Date createTime;      //创建时间

    protected Long modifier;      //修改人

    protected Date modifyTime;      //修改时间

    public Long getCreator() {
        return creator;
    }

    public void setCreator(Long creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getModifier() {
        return modifier;
    }

    public void setModifier(Long modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
