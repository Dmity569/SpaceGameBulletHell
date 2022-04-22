package com.example.spacegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShopActivity extends AppCompatActivity {
    SharedPreferences mSettings;
    TextView moneyShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_shop);
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        moneyShop = (TextView) findViewById(R.id.moneyShop);
        moneyShop.setText(Integer.toString(mSettings.getInt("money", 0)) + " G");
        Button ships = (Button) findViewById(R.id.ships);
        ships.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ShopActivity.this, ShipsActivity.class);
                startActivity(i);
            }
        });
    }
}
