package com.groot.gowl.ArraysPage;

import java.io.Serializable;

public class SerialPage_Arrays implements Serializable {

    private String urlPage;
    private String imgPage;
    private String sesons;
    private String nameSpace;

    public SerialPage_Arrays(String urlPage, String imgPage,String sesons,String nameSpace) {
        this.urlPage = urlPage;
        this.imgPage = imgPage;
        this.sesons = sesons;
        this.nameSpace = nameSpace;
    }

    public String getUrlPage() {
        return urlPage;
    }

    public void setUrlPage(String urlPage) {
        this.urlPage = urlPage;
    }

    public String getImgPage() {
        return imgPage;
    }

    public void setImgPage(String imgPage) {
        this.imgPage = imgPage;
    }

    public String getSesons() {
        return sesons;
    }

    public void setSesons(String sesons) {
        this.sesons = sesons;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

}
