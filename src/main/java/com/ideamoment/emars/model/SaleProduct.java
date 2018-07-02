package com.ideamoment.emars.model;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Chinakite on 2018/6/13.
 */
public class SaleProduct extends HistoriableEntity {
    private Long saleId;
    private Long productId;
    private String begin;
    private String end;
    private int section;
    private BigDecimal price;


    private ProductInfo product;
    private ArrayList<SaleContractFile> saleContractFiles = new ArrayList<SaleContractFile>();

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductInfo getProduct() {
        return product;
    }

    public void setProduct(ProductInfo product) {
        this.product = product;
    }

    public ArrayList<SaleContractFile> getSaleContractFiles() {
        return saleContractFiles;
    }

    public void setSaleContractFiles(ArrayList<SaleContractFile> saleContractFiles) {
        this.saleContractFiles = saleContractFiles;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }
}
