package com.groot.gowl.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.groot.gowl.ArraysPage.SerialPage_Arrays;
import com.groot.gowl.ArraysPage.StartPage_Arrays;
import com.groot.gowl.MainActivity;
import com.groot.gowl.R;
import com.groot.gowl.RecycleView_Adapters.SerialPage_Adapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class SerialActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private SerialPage_Adapter serialPage_adapter;
    private int count = 2;
    private boolean aBoolean = false;
    private String nextPage;
    private String bool;
    private ArrayList<SerialPage_Arrays> serialPage_arrays = new ArrayList<>();
    private Document document;
    private FloatingActionButton fab;
    private FloatingActionButton fab3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serial_activity);
        progressBar = findViewById(R.id.SerialPageProgressBar);
        recyclerView = findViewById(R.id.serialActivityRecycle);
        gridLayoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        serialPage_adapter = new SerialPage_Adapter(serialPage_arrays,this);
        recyclerView.setAdapter(serialPage_adapter);
        fab = findViewById(R.id.floatingActionButton2);
        fab3 = findViewById(R.id.floatingActionButton3);
        int orientation = this.getResources().getConfiguration().orientation;

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        nextPage = "https://uakino.club/seriesss/page/3/";
        bool = "https://uakino.club/seriesss/";

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    bool = "notPage";
                    String urlPosChange = "https://uakino.club/seriesss/page/1/";
                    StringBuffer stringBuffer = new StringBuffer(urlPosChange);
                    stringBuffer.replace(34,35, String.valueOf(count++));
                    nextPage = stringBuffer.toString();
                    Content content = new Content();
                    content.execute();

                    aBoolean = true;
                }
            }
        });
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(SerialActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        fab3.setOnClickListener(v -> {
            Intent intent = new Intent(SerialActivity.this, MultFilmActivity.class);
            startActivity(intent);
            finish();
        });

        Content content = new Content();
        content.execute();
    }





    private class Content  extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setAnimation(AnimationUtils.loadAnimation(SerialActivity.this, android.R.anim.fade_in));
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            progressBar.setAnimation(AnimationUtils.loadAnimation(SerialActivity.this, android.R.anim.fade_out));
            serialPage_adapter.notifyDataSetChanged();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (bool.equals("https://uakino.club/seriesss/")){
                try {
                    document = Jsoup.connect("https://uakino.club/seriesss/").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elements = document.select("div.movie-img");
                Elements nameSpace = document.select("a.movie-title");
                Elements imageElement = elements.select("img");
                Elements urlElement = elements.select("a");
                int size = elements.size();
                for (int i = 0;i<size;i++){
                    Element nameSpaces = nameSpace.get(i);
                    Element sesons = elements.get(i);
                    Element urlSize = urlElement.get(i);
                    Element images = imageElement.get(i);
                    String getUrlPage = urlSize.attr("href");
                    String getNameSpaces = nameSpaces.text();
                    String sesonsString = sesons.text();
                    String imageElementUrl = images.absUrl("src");
                    serialPage_arrays.add(new SerialPage_Arrays(getUrlPage,imageElementUrl,sesonsString,getNameSpaces));
                }
            }

            if (aBoolean){
                try {
                    document = Jsoup.connect(nextPage).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements elements = document.select("div.movie-img");
                Elements nameSpace = document.select("a.movie-title");
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
                    serialPage_arrays.add(new SerialPage_Arrays(getUrlPage,imageElementUrl,sesonsString,getNameSpaces));
                }
            }



            return null;
        }
    }
}
