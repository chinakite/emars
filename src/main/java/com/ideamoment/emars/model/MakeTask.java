package com.ideamoment.emars.model;

/**
 * Created by yukiwang on 2018/3/5.
 */
public class MakeTask extends HistoriableEntity {

    private String name;
    private long productId;
    private long makerId;
    private Integer timeSection;
    private Integer totalSection;
    private String showType;
    private Integer makeTime;
    private String showExpection;
    private long contractId;
    private String state;
    private String desc;
    private String extFinish;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getMakerId() {
        return makerId;
    }

    public void setMakerId(long makerId) {
        this.makerId = makerId;
    }

    public Integer getTimeSection() {
        return timeSection;
    }

    public void setTimeSection(Integer timeSection) {
        this.timeSection = timeSection;
    }

    public Integer getTotalSection() {
        return totalSection;
    }

    public void setTotalSection(Integer totalSection) {
        this.totalSection = totalSection;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public Integer getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Integer makeTime) {
        this.makeTime = makeTime;
    }

    public String getShowExpection() {
        return showExpection;
    }

    public void setShowExpection(String showExpection) {
        this.showExpection = showExpection;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getExtFinish() {
        return extFinish;
    }

    public void setExtFinish(String extFinish) {
        this.extFinish = extFinish;
    }
}
