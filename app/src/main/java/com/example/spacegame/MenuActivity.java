package com.example.spacegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    public SharedPreferences mSettings;
    public SharedPreferences shopData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);

        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();

        shopData = getSharedPreferences("shopData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorShop = shopData.edit();

        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, ChoiceActivity.class);
                startActivity(i);
            }
        });

        Button creators = findViewById(R.id.creators);
        creators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, CreatorsActivity.class);
                startActivity(i);
            }
        });

        Button settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, SettinsActivity.class);
                startActivity(i);
            }
        });


        Button recordList = findViewById(R.id.Record_lis_btn);
        recordList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, RecordActivity.class);
                startActivity(i);
            }
        });

        if (mSettings.getBoolean("firstLaunch", true)) {
            editor.clear();
            editor.putString("selectedShip", "Arrow");
            editor.putBoolean("firstLaunch", false);
            editor.apply();
            editorShop.clear();
            editorShop.putBoolean("Arrow", true);
            editorShop.apply();
        }

    }
}

