package com.groot.gowl.ArraysPage;

public class StartPage_Arrays {
    private String urlPage;
    private String imgPage;
    private String sesons;
    private String nameSpace;

    public StartPage_Arrays(String urlPage, String imgPage,String sesons,String nameSpace) {
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
