package com.groot.gowl.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.groot.gowl.ArraysPage.StartPage_Arrays;
import com.groot.gowl.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchActivity extends AppCompatActivity {
    private final Handler uiHandler = new Handler();
    private Document document;
    private EditText textInputLayout;
    private RecyclerView rcv_search;
    private Button button;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        button = findViewById(R.id.buttonSearch);
        webView = findViewById(R.id.webSearch);
        textInputLayout = findViewById(R.id.textView);
        rcv_search = findViewById(R.id.rcv_search);

        String url = "https://uakino.club/index.php?do=search";

        button.setOnClickListener(v -> {

            String tv = textInputLayout.getText().toString();
            final int interval = 1000; // 1 Second
            Handler handler = new Handler();
            Runnable runnable = new Runnable(){
                public void run() {
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setBlockNetworkImage(true);
                    webView.getSettings().setDomStorageEnabled(false);
                    webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
                    webView.getSettings().setLoadsImagesAutomatically(false);
                    webView.getSettings().setGeolocationEnabled(false);
                    webView.getSettings().setSupportZoom(false);
                    webView.addJavascriptInterface(new JSHtmlInterface(), "JSBridge");
                    WebSettings faller = webView.getSettings();
                    faller.setJavaScriptEnabled(true);
                    webView.setWebViewClient(new WebViewClient(){
                        public void onPageFinished(WebView view , String url){
                            view.loadUrl("javascript:(function(){document.getElementById('searchinput').value = '"+ tv +"';})();");
                            view.loadUrl("javascript:(function(){document.getElementById('dosearch').click();})();");
                        view.loadUrl("javascript:window.JSBridge.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                        }
                    });
                    webView.loadUrl(url);
                }
            };
            handler.postAtTime(runnable, System.currentTimeMillis()+interval);
            handler.postDelayed(runnable, interval);
            });

//        final int interval = 11000; // 1 Second
//        Handler handler = new Handler();
//        Runnable runnable = new Runnable(){
//            public void run() {
//                webView.destroy();
//            }
//        };
//        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
//        handler.postDelayed(runnable, interval);


    }

    class JSHtmlInterface {
        @android.webkit.JavascriptInterface
        public void showHTML(String html) {
            final String htmlContent = html;
            uiHandler.post(() -> {
                        document = Jsoup.parse(htmlContent);
                        Element element = document.select("div[id=dle-content]").first();
                        System.out.println(element);
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
                            System.out.println(imageElementUrl);
//                            System.out.println(getUrlPage + imageElementUrl+sesonsString+getNameSpaces);
//                            startPage_arrays.add(new StartPage_Arrays(getUrlPage,imageElementUrl,sesonsString,getNameSpaces));
                        }
                    }
            );
        }
    }
}