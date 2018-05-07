package com.ideamoment.emars.model;

public class ProductAnnouncer extends HistoriableEntity {
    private Long productId;
    private Long announcerId;
    private Long makeContractId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getAnnouncerId() {
        return announcerId;
    }

    public void setAnnouncerId(Long announcerId) {
        this.announcerId = announcerId;
    }

    public Long getMakeContractId() {
        return makeContractId;
    }

    public void setMakeContractId(Long makeContractId) {
        this.makeContractId = makeContractId;
    }
}
