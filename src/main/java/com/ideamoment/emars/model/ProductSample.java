package com.ideamoment.emars.model;

/**
 * Created by yukiwang on 2018/2/28.
 */
public class ProductSample extends BaseEntity{
    private long productId;
    private String fileUrl;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
