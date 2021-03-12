package com.groot.gowl.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.groot.gowl.MainActivity;
import com.groot.gowl.R;

public class BootLogo extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot_logo);
        new Handler().postDelayed(() -> {
            Intent mainIntent = new Intent(BootLogo.this, MainActivity.class);
            BootLogo.this.startActivity(mainIntent);
            BootLogo.this.finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}