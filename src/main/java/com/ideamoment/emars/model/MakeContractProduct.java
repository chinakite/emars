package com.ideamoment.emars.model;

import com.ideamoment.emars.utils.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by yukiwang on 2018/4/25.
 */
public class MakeContractProduct extends HistoriableEntity {

    private long makeContractId;
    private long productId;
    private String worker;
    private Long announcerId;
    private BigDecimal price;
    private Integer section;
    private ArrayList<MakeContractDoc> makeContractDocs = new ArrayList<>();
    private ArrayList<Announcer> announcers = new ArrayList<Announcer>();

    private ProductInfo product;

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

    public Long getAnnouncerId() {
        return announcerId;
    }

    public void setAnnouncerId(Long announcerId) {
        this.announcerId = announcerId;
    }

    public ArrayList<Announcer> getAnnouncers() {
        return announcers;
    }

    public void setAnnouncers(ArrayList<Announcer> announcers) {
        this.announcers = announcers;
    }

    public ProductInfo getProduct() {
        return product;
    }

    public void setProduct(ProductInfo product) {
        this.product = product;
    }

    public String getAnnouncersTextInline() {
        String result = "";
        if(announcers != null) {
            for (int i = 0; i < this.announcers.size(); i++) {
                Announcer announcer = announcers.get(i);
                if (i > 0) {
                    result += "、";
                }
                result += announcer.getName();
                if (StringUtils.isNotEmpty(announcer.getPseudonym())) {
                    result += "(" + announcer.getPseudonym() + ")";
                }
            }
        }
        return result;
    }
}
