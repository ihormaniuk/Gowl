package com.groot.gowl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.groot.gowl.Activity.SerialActivity;
import com.groot.gowl.ArraysPage.StartPage_Arrays;
import com.groot.gowl.RecycleView_Adapters.StartPage_Adapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private StartPage_Adapter startPage_adapter;
    private ProgressBar progressBar;
    private Document document;
    private ArrayList<StartPage_Arrays> startPage_arrays = new ArrayList<>();
    private int count = 2;
    private boolean aBoolean = false;
    private String nextPage;
    private String bool;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.StartPageProgressBar);

        recyclerView = findViewById(R.id.StartPage_RecyclerView);
        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        startPage_adapter = new StartPage_Adapter(startPage_arrays,MainActivity.this);
        recyclerView.setAdapter(startPage_adapter);
        fab = findViewById(R.id.floatingActionButton);
        int orientation = this.getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        nextPage = "https://uakino.club/page/3/";
        bool = "https://uakino.club/";


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    bool = "notPage";
                    String urlPosChange = "https://uakino.club/page/1/";
                    StringBuffer stringBuffer = new StringBuffer(urlPosChange);
                    stringBuffer.replace(25,27, String.valueOf(count++));
                    nextPage = stringBuffer.toString();
                    Content content = new Content();
                    content.execute();
                    recyclerView.setFocusable(true);
                    recyclerView.setFocusableInTouchMode(true);
                    aBoolean = true;
                }
            }
        });

        fab.setOnClickListener(v ->{
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            Intent intent = new Intent(MainActivity.this, SerialActivity.class);
            startActivity(intent);
            finish();
        });
        layoutAnimation(recyclerView);


        Content content = new Content();
        content.execute();


    }

    private void layoutAnimation(RecyclerView recyclerView){
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context,R.anim.layout_fal_down);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }


    private class Content  extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.setAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
            startPage_adapter.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (bool.equals("https://uakino.club/")){
                try {
                    document = Jsoup.connect("https://uakino.club/").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Element element = document.select("div[class=main-section-wr with-sidebar coloredgray clearfix]").first();
                Elements elements = element.select("div.movie-img");
//                Elements nameSpace = element.select("a.movie-title");
                Elements nameNotSerial = element.select("div.deck-title");
                Elements imageElement = elements.select("img");
                Elements urlElement = elements.select("a");
                int size = elements.size();
                for (int i = 0;i<size;i++){
                    Element nameSpaces = nameNotSerial.get(i);
                    Element sesons = elements.get(i);
                    Element urlSize = urlElement.get(i);
                    Element images = imageElement.get(i);
                    String getUrlPage = urlSize.attr("href");
                    String getNameSpaces = nameSpaces.text();
                    String sesonsString = sesons.text();
                    String imageElementUrl = images.absUrl("src");
                    startPage_arrays.add(new StartPage_Arrays(getUrlPage,imageElementUrl,sesonsString,getNameSpaces));
                }
            }

            if (aBoolean){
                try {
                    document = Jsoup.connect(nextPage).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Element element = document.select("div[class=main-section-wr with-sidebar coloredgray clearfix]").first();
                Elements elements = element.select("div.movie-img");
                Elements nameSpace = element.select("a.movie-title");
                Elements imageElement = elements.select("img");
                Elements urlElement = elements.select("a");
                int size = elements.size();
                for (int i = 0;i<size;i++){
                    Element sesons = elements.get(i);
                    Element urlSize = urlElement.get(i);
                    String getUrlPage = urlSize.attr("href");
                    Element images = imageElement.get(i);
                    Element nameSpaces = nameSpace.get(i);
                    String getNameSpaces = nameSpaces.text();
                    String sesonsString = sesons.text();
                    String imageElementUrl = images.absUrl("src");
                    startPage_arrays.add(new StartPage_Arrays(getUrlPage,imageElementUrl,sesonsString,getNameSpaces));
                }
            }
            return null;
        }
    }


}