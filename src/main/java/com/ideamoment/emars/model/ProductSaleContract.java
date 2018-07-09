package com.ideamoment.emars.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yukiwang on 2018/5/3.
 */
public class ProductSaleContract {
    private String code;
    private String type;
    private String granterName;
    private String customerName;
    private String platformNames;
    private BigDecimal price;
    private Integer section;
    private List<SaleContractFile> saleContractFiles = new ArrayList<>();
    private Integer totalSection;
    private BigDecimal totalPrice;
    private long productId;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGranterName() {
        return granterName;
    }

    public void setGranterName(String granterName) {
        this.granterName = granterName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPlatformNames() {
        return platformNames;
    }

    public void setPlatformNames(String platformNames) {
        this.platformNames = platformNames;
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

    public List<SaleContractFile> getSaleContractFiles() {
        return saleContractFiles;
    }

    public void setSaleContractFiles(List<SaleContractFile> saleContractFiles) {
        this.saleContractFiles = saleContractFiles;
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

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
