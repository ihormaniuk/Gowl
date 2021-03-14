package com.groot.gowl.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.groot.gowl.MainActivity;

public class ActivityOpenBrowser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Uri data = intent.getData();
        String url = data.toString();
        try {
            if (url.equals("https://uakino.club/")){
                Intent main = new Intent(ActivityOpenBrowser.this, MainActivity.class);
                startActivity(main);
            }else if (url.equals("https://uakino.club/seriesss/")){
                Intent serial = new Intent(ActivityOpenBrowser.this, SerialActivity.class);
                startActivity(serial);
            }
            else if (url.equals("https://uakino.club/cartoon/")){
                Intent cartoon = new Intent(ActivityOpenBrowser.this, MultFilmActivity.class);
                startActivity(cartoon);
            }
            else if (url.contains("seriesss")) {
                Intent serialAct = new Intent(ActivityOpenBrowser.this, SelectActivity_Serials.class);
                serialAct.putExtra("url",url);
                startActivity(serialAct);

            }else if (url.contains("cartoonseries")) {
                Intent cartoonser = new Intent(ActivityOpenBrowser.this, SelectActivity_Serials.class);
                cartoonser.putExtra("url", url);
                startActivity(cartoonser);
            }
            else if (url.contains("cartoon")) {
                Intent carton = new Intent(ActivityOpenBrowser.this, SelectActivity_Fiml.class);
                carton.putExtra("url", url);
                startActivity(carton);
            }
            else if (url.contains("anime-series")) {
                Intent serialAct = new Intent(ActivityOpenBrowser.this, SelectActivity_Serials.class);
                serialAct.putExtra("url", url);
                startActivity(serialAct);
            }
            System.out.println(url);

        } catch (Exception e) {
            Toast.makeText(ActivityOpenBrowser.this,"Some Wrong",Toast.LENGTH_LONG).show();
        }


    }

}
