package com.ideamoment.emars.model;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by yukiwang on 2018/4/25.
 */
public class MakeContract extends HistoriableEntity {

    private String code;
    private String targetType;
    private String owner;
    private String maker;
    private Long makerId;
    private Integer totalSection;
    private BigDecimal totalPrice;
    private String signDate;
    private ArrayList<MakeContractProduct> mcProducts = new ArrayList<MakeContractProduct>();
    private String mcProductIds;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Integer getTotalSection() {
        return totalSection;
    }

    public void setTotalSection(Integer totalSection) {
        this.totalSection = totalSection;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public ArrayList<MakeContractProduct> getMcProducts() {
        return mcProducts;
    }

    public void setMcProducts(ArrayList<MakeContractProduct> mcProducts) {
        this.mcProducts = mcProducts;
    }

    public String getMcProductIds() {
        return mcProductIds;
    }

    public void setMcProductIds(String mcProductIds) {
        this.mcProductIds = mcProductIds;
    }

    public Long getMakerId() {
        return makerId;
    }

    public void setMakerId(Long makerId) {
        this.makerId = makerId;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }
}
