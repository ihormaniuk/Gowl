package com.groot.gowl.Exoplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.groot.gowl.ArraysPage.ArraysPageSerial;
import com.groot.gowl.R;

import java.util.ArrayList;

public class ExoActivity extends AppCompatActivity {

    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;
    private String url;
    private long position;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;
    private boolean flag = false;
    ProgressBar progressBar;
    ImageView bt_fullscreen;
    ImageView bt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exo_player);
        Intent getInt = getIntent();
        url = getInt.getStringExtra("url");
        bt_fullscreen = findViewById(R.id.bt_fullscreen);
        bt_next = findViewById(R.id.exo_nextTrac);
        bt_fullscreen.setOnClickListener(v -> {
            if (flag){
                bt_fullscreen.setImageDrawable(getResources().getDrawable(R.drawable.exo_controls_fullscreen_enter));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                flag = false;
            }else {
                bt_fullscreen.setImageDrawable(getResources().getDrawable(R.drawable.exo_controls_fullscreen_exit));
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                flag =true;
            }
        });

    }



    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        simpleExoPlayer.setPlayWhenReady(true);
        if (position != C.TIME_UNSET) simpleExoPlayer.seekTo(position);
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || simpleExoPlayer == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        position = simpleExoPlayer.getCurrentPosition();
        simpleExoPlayer.setPlayWhenReady(false);
        super.onPause();
        if (simpleExoPlayer != null) {
            position = simpleExoPlayer.getCurrentPosition();
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }


    private void initializePlayer() {
        playerView = findViewById(R.id.player_view);
        progressBar = findViewById(R.id.progress_bar);
        bt_fullscreen = findViewById(R.id.bt_fullscreen);
        simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        Uri uri = Uri.parse(url);
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSourceFactory();
        HlsMediaSource hlsMediaSource =
                new HlsMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(MediaItem.fromUri(uri));
        simpleExoPlayer.setMediaSource(hlsMediaSource);
        playerView.setPlayer(simpleExoPlayer);
        playbackPosition = simpleExoPlayer.getCurrentPosition();
        currentWindow = simpleExoPlayer.getCurrentWindowIndex();
        playWhenReady = simpleExoPlayer.getPlayWhenReady();
        simpleExoPlayer.prepare();
        simpleExoPlayer.play();
        playerView.setPlayer(simpleExoPlayer);
        simpleExoPlayer.setPlayWhenReady(playWhenReady);
        simpleExoPlayer.seekTo(currentWindow, playbackPosition);



        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING){
                    progressBar.setVisibility(View.VISIBLE);
                }else if(playbackState==Player.STATE_READY){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
    }

    private void releasePlayer() {
        if (simpleExoPlayer != null) {
            playbackPosition = simpleExoPlayer.getCurrentPosition();
            currentWindow = simpleExoPlayer.getCurrentWindowIndex();
            playWhenReady = simpleExoPlayer.getPlayWhenReady();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

}
