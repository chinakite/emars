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
    private String worker;
    private String maker;
    private Integer totalSection;
    private BigDecimal totalPrice;
    private ArrayList<MakeContractProduct> products = new ArrayList<MakeContractProduct>();
    private Long[] productIds;


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

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
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

    public ArrayList<MakeContractProduct> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<MakeContractProduct> products) {
        this.products = products;
    }

    public Long[] getProductIds() {
        return productIds;
    }

    public void setProductIds(Long[] productIds) {
        this.productIds = productIds;
    }
}
