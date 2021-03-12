package com.groot.gowl.Activity;

import androidx.appcompat.app.AppCompatActivity;

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
import com.groot.gowl.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class SearchActivity extends AppCompatActivity {
    private final Handler uiHandler = new Handler();
    private Document document;
    private EditText textInputLayout;


    class JSHtmlInterface {
        @android.webkit.JavascriptInterface
        public void showHTML(String html) {
            final String htmlContent = html;
            uiHandler.post(() -> {

                        document = Jsoup.parse(htmlContent);

                        System.out.println(document);


            }
            );
        }
    }


    private Button button;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        button = findViewById(R.id.buttonSearch);
        webView = findViewById(R.id.webSearch);
        textInputLayout = findViewById(R.id.textView);


//        String s = tv;






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
                    String url = "https://uakino.club/index.php?do=search";
                    faller.setJavaScriptEnabled(true);
                    webView.loadUrl(url);
                    webView.setWebViewClient(new WebViewClient(){
                        public void onPageFinished(WebView view , String url){
                            view.loadUrl("javascript:(function(){document.getElementById('searchinput').value = '"+ tv +"';})();");
                            view.loadUrl("javascript:(function(){document.getElementById('dosearch').click();})();");
//                        view.loadUrl("javascript:window.JSBridge.showHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
                        }
                    });
                }
            };
            handler.postAtTime(runnable, System.currentTimeMillis()+interval);
            handler.postDelayed(runnable, interval);
            });

        final int interval = 9000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable(){
            public void run() {
                webView.destroy();
            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis()+interval);
        handler.postDelayed(runnable, interval);


    }

    public void searchBt(View view) {
    }
}