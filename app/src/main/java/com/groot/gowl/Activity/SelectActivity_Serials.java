package com.groot.gowl.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.groot.gowl.ArraysPage.ArraysPageSerial;
import com.groot.gowl.ArraysPage.ImageArrays_Head;
import com.groot.gowl.ArraysPage.PageSerialsAndSezons;
import com.groot.gowl.R;
import com.groot.gowl.RecycleView_Adapters.ImageAdapter_Head;
import com.groot.gowl.RecycleView_Adapters.SeriasAdaptersAndSezons;
import com.groot.gowl.RecycleView_Adapters.Series_Adapters;
import com.squareup.picasso.Picasso;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SelectActivity_Serials extends AppCompatActivity {
    private ProgressBar progressBar;
    private ImageView imageView;
    private RecyclerView recyclerViewHeadImage;
    private RecyclerView recyclerViewSeries;
    private RecyclerView recyclerViewSeasons;
    private ImageAdapter_Head imageAdapter_head;
    private Series_Adapters series_adapters;
    private final ArrayList<ImageArrays_Head> arraysImage = new ArrayList<>();
    private ArrayList<ArraysPageSerial> serials = new ArrayList<>();
    private TextView textView;
    private Intent intent;
    private Document document;
    private String img;
    private String url;
    private String name;
    private WebView webView;
    private Element sesonse;
    private Runnable runnable;
    private Thread thread;
    private ArrayList<PageSerialsAndSezons> pageSerialsAndSezons = new ArrayList<>();
    private String pages;
    private String urlPageSerial;
    private final Handler uiHandler = new Handler();
    private int sized;
    private SeriasAdaptersAndSezons seriasAdaptersAndSezons;
    private LinearLayoutManager linearLayoutManager;



    class JSHtmlInterface {
        @android.webkit.JavascriptInterface
        public void showHTML(String html) {
            final String htmlContent = html;
            uiHandler.post(
                    () -> {
                        try {
                            document = Jsoup.parse(htmlContent);
                            sesonse = document.select("ul[class=seasons clearfix]").first();
                            Element div_playlistsVideos = document.select("div.playlists-videos").first();
                            if (sesonse == null) {

                            } else {
                                Elements esdsfff = sesonse.select("li").select("a[href]");
                                int size1 = esdsfff.size();
                                for (int i = 0; i < size1; i++) {
                                    Element el = esdsfff.get(i);
                                    String textSisons = el.text();
                                    String urlSizons2 = el.attr("href");
                                    pageSerialsAndSezons.add(new PageSerialsAndSezons(urlSizons2, textSisons,img));
                                    System.out.println("Text Sezons  :: " + textSisons + "\n " + "Url Sezons : " + urlSizons2);
                                }
                            }
                            try {
                                Element efg = div_playlistsVideos.select("div.playlists-items").first();
                                Elements rg = efg.select("li");
                                System.out.println("li :: "+ rg);
                                sized = rg.size();
                                for (int i = 0; i < sized; i++) {
                                    Element tit = rg.get(i);
                                    String tr = tit.text();
                                    Element element = rg.get(i);
                                    String as = element.absUrl("data-file");
                                    runnable = () -> {
                                        try {
                                            document = Jsoup.connect(as).get();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        String getResourcesMatcher = document.toString();
                                        String pattern = ".*file:\"([^,]*),";
                                        Pattern patternClass = Pattern.compile(pattern);
                                        Matcher matcherClass = patternClass.matcher(getResourcesMatcher);
                                        if (matcherClass.find()) {
                                            String resFileUrl = matcherClass.group(0);
                                            String delElement = resFileUrl.replaceAll("\\s+", "");
                                            String subString = delElement.substring(6);
                                            pages = subString.substring(0, subString.length() - 2);
                                            urlPageSerial = pages;
                                            serials.add(new ArraysPageSerial(pages, tr, img));
                                            runOnUiThread(() -> {
                                                series_adapters.notifyDataSetChanged();
                                                seriasAdaptersAndSezons = new SeriasAdaptersAndSezons(pageSerialsAndSezons,SelectActivity_Serials.this);
                                                recyclerViewSeasons.setAdapter(seriasAdaptersAndSezons);
                                                System.out.println("serials.size() " + serials.size());
                                                if (serials.size() == sized) {
                                                    webView.destroy();
                                                    series_adapters.notifyDataSetChanged();
                                                    progressBar.setVisibility(View.GONE);
                                                }

                                            });
                                        }
                                    };
                                    thread = new Thread(runnable);
                                    thread.start();
                                }
                            } catch (Exception e) {
                                webView.stopLoading();
                                webView.clearHistory();
                                webView.clearCache(true);
                                webView.destroy();
                                series_adapters.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SelectActivity_Serials.this,"Немає Контенту",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(SelectActivity_Serials.this,"Some wrong",Toast.LENGTH_SHORT).show();
                        }

                    }
            );
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_activity_serials);
        textView = findViewById(R.id.tv_SelectPage_Serial);
        imageView = findViewById(R.id.imageSelectSerial);
        progressBar = findViewById(R.id.progressBarSelectActivitySerial);
        intent = getIntent();
        url = intent.getStringExtra("url");
        name  =intent.getStringExtra("name");
        recyclerViewHeadImage = findViewById(R.id.recycleSelectImgHead);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewHeadImage.setLayoutManager(linearLayoutManager);
        recyclerViewHeadImage.setHasFixedSize(true);
        recyclerViewSeries = findViewById(R.id.selectSeries);
        recyclerViewSeasons = findViewById(R.id.selectSisons);
        LinearLayoutManager liner = new LinearLayoutManager(SelectActivity_Serials.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayo = new LinearLayoutManager(SelectActivity_Serials.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewSeries.setLayoutManager(liner);
        recyclerViewSeries.setHasFixedSize(true);
        recyclerViewSeasons.setLayoutManager(linearLayo);
        recyclerViewSeasons.setHasFixedSize(true);
        webView = findViewById(R.id.webViews);
        webView.setWebViewClient(
                new WebViewClient() {

                    @Override
                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                        progressBar.setVisibility(View.VISIBLE);
                        super.onPageStarted(view, url, favicon);
                    }
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        webView.loadUrl("javascript:window.JSBridge.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                    }
                }
        );
        webView.addJavascriptInterface(new JSHtmlInterface(), "JSBridge");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBlockNetworkImage(true);
        webView.getSettings().setDomStorageEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setLoadsImagesAutomatically(false);
        webView.getSettings().setGeolocationEnabled(false);
        webView.getSettings().setSupportZoom(false);
        webView.loadUrl(url);
        imageAdapter_head = new ImageAdapter_Head(arraysImage, SelectActivity_Serials.this);
        Content content = new Content();
        content.execute();
    }


    @SuppressLint("StaticFieldLeak")
    private class Content extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String aVoid) {
            super.onPostExecute(aVoid);
            Picasso.get().load(img).into(imageView);
            recyclerViewHeadImage.setAdapter(imageAdapter_head);
            series_adapters = new Series_Adapters(serials, SelectActivity_Serials.this);
            recyclerViewSeries.setAdapter(series_adapters);
        }
        @SuppressLint("SetJavaScriptEnabled")
        @Override
        protected String doInBackground(Void... voids) {
            //image
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(()->{
                try {
                    Element element = document.select("span.solototle").first();
                    System.out.println(element.text());
                    textView.setText(element.text());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            try {
                Element nameNotSerial = document.select("div.film-poster").first();
                Element imageUrl = nameNotSerial.select("img").first();
                img = imageUrl.absUrl("src");
                Element screensection = document.select("div.screens-section").first();
                Elements imgScreen = screensection.select("a");
                int s = imgScreen.size();
                for (int i = 0; i < s; i++) {
                    Element elementUrl = imgScreen.get(i);
                    String imgUrl = elementUrl.absUrl("href");
                    arraysImage.add(new ImageArrays_Head(imgUrl));
                    System.out.println(imgUrl);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return urlPageSerial;
        }
    }
}
