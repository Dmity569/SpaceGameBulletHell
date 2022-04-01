package com.example.spacegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettinsActivity extends AppCompatActivity {
    SharedPreferences mSettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);


        Button set_settings = findViewById(R.id.set_settins);
        set_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {












                onBackPressed();
            }
        });
    }
}
