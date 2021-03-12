package com.groot.gowl.ArraysPage;

import java.io.Serializable;

public class PageSerialsAndSezons implements Serializable {
    private String urlSesons;
    private String infoSesons;
    private String img;


    public PageSerialsAndSezons( String urlSesons, String infoSesons,String img) {
        this.urlSesons = urlSesons;
        this.infoSesons = infoSesons;
        this.img = img;
    }


    public String getUrlSesons() {
        return urlSesons;
    }

    public void setUrlSesons(String urlSesons) {
        this.urlSesons = urlSesons;
    }

    public String getInfoSesons() {
        return infoSesons;
    }

    public void setInfoSesons(String infoSesons) {
        this.infoSesons = infoSesons;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}