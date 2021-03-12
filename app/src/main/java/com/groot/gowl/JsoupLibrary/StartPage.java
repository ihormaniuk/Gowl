package com.groot.gowl.JsoupLibrary;

import android.util.Log;

import com.groot.gowl.ArraysPage.StartPage_Arrays;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class StartPage {
    private static final String TAG_T = "T_START_PAGE";
    private final String urlPage;
    private int sizeElements;
    private  String test;
    private Document document;
    private ArrayList<StartPage_Arrays> arraysElement;

    public StartPage(String urlPage){
        this.urlPage = urlPage;
        Connect();
    }

    private Document Connect(){
        try {
            document = Jsoup.connect(urlPage).get();
        } catch (IOException e) {
            Log.d(TAG_T,"Error Connect");
        }
        return document;
    }


    public void elementPage(){
        Elements elements = document.select("div.movie-img");
        Elements imageElement = elements.select("img");
        Elements urlElement = elements.select("a");
        arraysElement = new ArrayList<>();
        int size = elements.size();
        sizeElements = size;
        for (int i = 0;i<size;i++){
            Element urlSize = urlElement.get(i);
            String getUrlPage = urlSize.attr("href");
            Element images = imageElement.get(i);
            String imageElementUrl = images.absUrl("src");
//            arraysElement.add(new StartPage_Arrays(getUrlPage,imageElementUrl),);
        }
    }


    public ArrayList<StartPage_Arrays> getArrays(){
        elementPage();
        return arraysElement;
    }

    public int getSize(){
        return sizeElements;
    }


}
