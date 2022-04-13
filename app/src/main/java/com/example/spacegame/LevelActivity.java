package com.example.spacegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.prefs.Preferences;

public class LevelActivity extends AppCompatActivity {
    public SharedPreferences mSettings;
    public SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int level_id = getIntent().getExtras().getInt("level_id");
        mSettings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        editor = mSettings.edit();
        setContentView(new DrawView(this, this));
        Toast.makeText(LevelActivity.this, "Level - " + Integer.toString(level_id + 1),
                Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        editor.apply();
    }
}

