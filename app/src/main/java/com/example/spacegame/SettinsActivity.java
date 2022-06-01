package com.example.spacegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class SettinsActivity extends AppCompatActivity {
    public SharedPreferences mSettings;

    MediaPlayer mp;

    @RequiresApi(api = Build.VERSION_CODES.O)

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

//        float volume = (float) mSettings.getInt("music", 50);
//
//        mp = MediaPlayer.create(this, R.raw.menu);
//        mp.setLooping(true);
//        mp.seekTo(0);
//
//        int MAX_VOLUME = 100;
//
//        final float volume_x = (float) (1 - (Math.log(MAX_VOLUME - volume) / Math.log(MAX_VOLUME)));
//        mp.setVolume(volume_x, volume_x);
//
//        mp.start();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);

        SeekBar music = findViewById(R.id.seekBar_фоновая_музыка);
        music.setMin(0); //%
        music.setMax(100); //%
        music.setProgress(mSettings.getInt("music", 50));

        SeekBar sound = findViewById(R.id.seekBar_звук);
        sound.setMin(0); //%
        sound.setMax(99); //%
        sound.setProgress(mSettings.getInt("sound", 50));

        Switch Switch_альтернативное_управление = findViewById(R.id.Switch_альтернативное_управление);
        Switch_альтернативное_управление.setChecked(mSettings.getBoolean("альтернативное_управление", false));

//        EditText editTextname = (EditText) findViewById(R.id.textView8);
//        editTextname.setText(mSettings.getString("user_name", "Select_your_name"));
//
//        EditText editTextsupercode = (EditText) findViewById(R.id.textView9);
//        editTextsupercode.setText("0");


        Button set_settings = findViewById(R.id.set_settins);
        set_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("music", music.getProgress());
                editor.putInt("sound", sound.getProgress());
                editor.putBoolean("альтернативное_управление", Switch_альтернативное_управление.isChecked());
//                editor.putString("user_name", editTextname.getText().toString());
//                System.out.println(editTextname.getText().toString());
//                if (editTextname.getText().toString() == "Chezare"){
//                    System.out.println("xx");
//                    if (editTextsupercode.getText().toString() != "1"){
//                        editor.putString("user_name", "No!!!");
//                        System.out.println("xxx");
//                    }
//                }
//                if (editTextsupercode.getText().toString() == "1"){ editor.putBoolean("Supercode", true);}
//                else {editor.putBoolean("Supercode", false);}
                editor.apply();
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();

    }
    @Override
    protected void onUserLeaveHint() {
        mp.stop();
        super.onUserLeaveHint();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mp.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        float volume = (float) mSettings.getInt("music", 50);

        mp = MediaPlayer.create(this, R.raw.menu);
        mp.setLooping(true);
        mp.seekTo(0);

        int MAX_VOLUME = 100;

        final float volume_x = (float) (1 - (Math.log(MAX_VOLUME - volume) / Math.log(MAX_VOLUME)));
        mp.setVolume(volume_x, volume_x);
        mp.start();
    }
}
