package com.example.spacegame;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LevelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new DrawView(this));
        int level_id = getIntent().getExtras().getInt("level_id");
        Toast.makeText(LevelActivity.this, "Level - " + Integer.toString(level_id + 1),
                Toast.LENGTH_SHORT).show();
    }
}

