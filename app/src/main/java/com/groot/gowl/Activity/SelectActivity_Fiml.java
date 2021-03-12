package com.groot.gowl.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.groot.gowl.ArraysPage.ImageArrays_Head;
import com.groot.gowl.Exoplayer.ExoActivity;
import com.groot.gowl.R;
import com.groot.gowl.RecycleView_Adapters.ImageAdapter_Head;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectActivity_Fiml extends AppCompatActivity {
    private Document document;
    private RecyclerView recyclerView;
    private ArrayList<ImageArrays_Head> arraysImage = new ArrayList<>();
    private ImageAdapter_Head imageAdapter_head;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private ImageView imageView;
    private TextView textView;
    private Intent intent;
    private String url;
    private String name;
    private String img;
    private String urlFilm;
    private TextView textViewOfFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity_film);
        textView = findViewById(R.id.tv_SelectPage_Film);
        imageView = findViewById(R.id.imageSelectFilm);
        progressBar = findViewById(R.id.progressBarSelectActivity);
        recyclerView = findViewById(R.id.recycleSelectImgHead);
        textViewOfFilm = findViewById(R.id.textViewOfFilm);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        intent = getIntent();
        url = intent.getStringExtra("url");
        name  =intent.getStringExtra("name");
        textView.setText(name);
        Content content = new Content();
        content.execute();
    }





    private class Content  extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setAnimation(AnimationUtils.loadAnimation(SelectActivity_Fiml.this, android.R.anim.fade_in));
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBar.setVisibility(View.GONE);
            Picasso.get().load(img).into(imageView);
            imageAdapter_head = new ImageAdapter_Head(arraysImage,SelectActivity_Fiml.this);
            recyclerView.setAdapter(imageAdapter_head);
            imageView.setOnClickListener(v -> {
                if (urlFilm==null){
                    Toast.makeText(SelectActivity_Fiml.this,"Some Wrong",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(SelectActivity_Fiml.this, ExoActivity.class);
                    intent.putExtra("url",urlFilm);
                    startActivity(intent);
                }
            });
            progressBar.setAnimation(AnimationUtils.loadAnimation(SelectActivity_Fiml.this, android.R.anim.fade_out));
            imageAdapter_head.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
                try {
                    document = Jsoup.connect(url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    Elements element1 = document.select("div[class=full-text clearfix ]");
                    textViewOfFilm.setText(element1.text());
                });

                Elements head = document.select("div.speedbar");
                Elements hea = head.select("span.itemprop");

                    Element nameNotSerial = document.select("div.film-poster").first();
                    Element imageUrl = nameNotSerial.select("img").first();
                    img = imageUrl.absUrl("src");

                    Element screensection = document.select("div.screens-section").first();
                    Elements imgScreen = screensection.select("a");
                    int s = imgScreen.size();
                    for (int i = 0;i<s;i++){
                        Element elementUrl = imgScreen.get(i);
                        String imgUrl = elementUrl.absUrl("href");
                        arraysImage.add(new ImageArrays_Head(imgUrl));
                        System.out.println(imgUrl);
                    }
                    getUrlFilm();
                    return null;
        }
    }

    public String getUrlFilm(){
        Element iframeElement = document.select("iframe").first();
        String iframe_Src = iframeElement.attr("src");
        if (iframe_Src != null) {
            try {
                document = Jsoup.connect(iframe_Src).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String getResourcesMatcher = document.toString();
            String pattern = ".*file:\"([^,]*),";
            Pattern patternClass = Pattern.compile(pattern);
            Matcher matcherClass = patternClass.matcher(getResourcesMatcher);
            if (matcherClass.find( )) {
                String resFileUrl = matcherClass.group(0);
                String delElement = resFileUrl.replaceAll("\\s+", "");
                String subString = delElement.substring(6);
                urlFilm = subString.substring(0, subString.length() - 2);
                System.out.println("Url Pages : " + urlFilm);
            }
        }
        return urlFilm;
    }
}
