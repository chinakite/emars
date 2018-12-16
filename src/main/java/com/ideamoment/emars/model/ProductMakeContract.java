package com.ideamoment.emars.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukiwang on 2018/5/3.
 */
public class ProductMakeContract {
    private String code;
    private String targetType;
    private String owner;
    private String maker;
    private String worker;
    private BigDecimal price;
    private Integer section;
    private List<MakeContractDoc> makeContractDocs = new ArrayList<>();
    private Integer totalSection;
    private BigDecimal totalPrice;
    private long mcProductId;

    private MakeContractProduct makeProduct;


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

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public List<MakeContractDoc> getMakeContractDocs() {
        return makeContractDocs;
    }

    public void setMakeContractDocs(List<MakeContractDoc> makeContractDocs) {
        this.makeContractDocs = makeContractDocs;
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

    public long getMcProductId() {
        return mcProductId;
    }

    public void setMcProductId(long mcProductId) {
        this.mcProductId = mcProductId;
    }

    public MakeContractProduct getMakeProduct() {
        return makeProduct;
    }

    public void setMakeProduct(MakeContractProduct makeProduct) {
        this.makeProduct = makeProduct;
    }
}
