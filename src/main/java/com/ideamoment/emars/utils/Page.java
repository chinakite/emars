package com.ideamoment.emars.utils;

import java.util.List;

/**
 * Created by Chinakite on 2018/2/16.
 */
public class Page<T> {
    private int currentPage;
    private int pageSize;
    private long totalRecord;

    private List<T> data;

    /**
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * @param currentPage the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @return the totalPage
     */
    public int getTotalPage() {
        return (int)Math.ceil((double)totalRecord/(double)pageSize);
    }

    /**
     * @return the totalRecord
     */
    public long getTotalRecord() {
        return totalRecord;
    }

    /**
     * @param totalRecord the totalRecord to set
     */
    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }
}
