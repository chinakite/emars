package com.ideamoment.emars.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yukiwang on 2018/4/25.
 */
public class MakeContractProduct extends BaseEntity {

    private long makeContractId;
    private long productId;
    private String worker;
    private BigDecimal price;
    private Integer section;
    private Long creator;
    private Date createTime;
    private ArrayList<MakeContractDoc> makeContractDocs = new ArrayList<>();

    public long getMakeContractId() {
        return makeContractId;
    }

    public void setMakeContractId(long makeContractId) {
        this.makeContractId = makeContractId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
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

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public ArrayList<MakeContractDoc> getMakeContractDocs() {
        return makeContractDocs;
    }

    public void setMakeContractDocs(ArrayList<MakeContractDoc> makeContractDocs) {
        this.makeContractDocs = makeContractDocs;
    }
}
