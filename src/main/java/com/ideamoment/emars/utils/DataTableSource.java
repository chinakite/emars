package com.ideamoment.emars.utils;

import java.util.List;

/**
 * Created by Chinakite on 2018/2/17.
 */
public class DataTableSource<T> {
    private int draw;

    private long recordsTotal;

    private long recordsFiltered;

    private List<T> data;


    /**
     * @return the draw
     */
    public int getDraw() {
        return draw;
    }


    /**
     * @param draw the draw to set
     */
    public void setDraw(int draw) {
        this.draw = draw;
    }


    /**
     * @return the recordsTotal
     */
    public long getRecordsTotal() {
        return recordsTotal;
    }


    /**
     * @param recordsTotal the recordsTotal to set
     */
    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }


    /**
     * @return the recordsFiltered
     */
    public long getRecordsFiltered() {
        return recordsFiltered;
    }


    /**
     * @param recordsFiltered the recordsFiltered to set
     */
    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
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
