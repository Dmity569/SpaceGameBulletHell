package com.example.spacegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class SettinsActivity extends AppCompatActivity {
    SharedPreferences mSettings;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        SeekBar music = findViewById(R.id.seekBar_фоновая_музыка);
        music.setMin(0); //%
        music.setMax(100); //%
        music.setProgress(mSettings.getInt("music", 50));

        SeekBar sound = findViewById(R.id.seekBar_звук);
        sound.setMin(0); //%
        sound.setMax(100); //%
        sound.setProgress(mSettings.getInt("sound", 50));

        Button set_settings = findViewById(R.id.set_settins);
        set_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("music", music.getProgress());
                editor.putInt("sound", sound.getProgress());
                editor.apply();
                onBackPressed();
            }
        });
    }
}
