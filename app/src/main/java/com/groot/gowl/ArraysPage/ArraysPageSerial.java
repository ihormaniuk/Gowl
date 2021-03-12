package com.groot.gowl.ArraysPage;

import java.io.Serializable;

public class ArraysPageSerial implements Serializable {
    private String seriesUrl;
    private String infoSeries;
    private String image;


    public ArraysPageSerial(String seriesUrl, String infoSeries,String image) {
        this.seriesUrl = seriesUrl;
        this.infoSeries = infoSeries;
        this.image = image;
    }

    public ArraysPageSerial(String image) {
        this.image = image;
    }




    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSeriesUrl() {
        return seriesUrl;
    }

    public void setSeriesUrl(String seriesUrl) {
        this.seriesUrl = seriesUrl;
    }

    public String getInfoSeries() {
        return infoSeries;
    }

    public void setInfoSeries(String infoSeries) {
        this.infoSeries = infoSeries;
    }

}