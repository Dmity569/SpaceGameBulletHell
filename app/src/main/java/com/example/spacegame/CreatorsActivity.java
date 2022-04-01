package com.example.spacegame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreatorsActivity extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_creators);
        mp = MediaPlayer.create(this, R.raw.creators_main_frame);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(1.0f, 1.0f);
        mp.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();

    }
}


