package com.ideamoment.emars.model;

/**
 * Created by yukiwang on 2018/5/15.
 */
public class ReservationAnnouncer extends BaseEntity {
    private long productId;
    private long announcerId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getAnnouncerId() {
        return announcerId;
    }

    public void setAnnouncerId(long announcerId) {
        this.announcerId = announcerId;
    }
}
