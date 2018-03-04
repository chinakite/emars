package com.ideamoment.emars.model;

import java.math.BigDecimal;

/**
 * Created by yukiwang on 2018/3/5.
 */
public class CopyrightContractProduct extends BaseEntity {

    private long contractId;
    private long productId;
    private BigDecimal price;

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
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
}
