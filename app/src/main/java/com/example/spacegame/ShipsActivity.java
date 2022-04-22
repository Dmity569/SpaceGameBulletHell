package com.example.spacegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShipsActivity extends AppCompatActivity {
    SharedPreferences mSettings;
    TextView moneyShips;
    LinearLayout shipButtons;
    EditorInfo.ShipType shipType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ships);
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        moneyShips = (TextView) findViewById(R.id.moneyShips);
        moneyShips.setText(Integer.toString(mSettings.getInt("money", 0)) + " G");
        shipButtons = (LinearLayout) findViewById(R.id.shipButtons);
        for (int i = 0; i < EditorInfo.ship_amount; i++){
            Button ship_button = (Button) shipButtons.getChildAt(i);
            ship_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch ((String) ship_button.getText()){
                        case "Arrow":
                            shipType = new EditorInfo.Ship_Arrow();
                            break;
                    }
                }
            }
                );
        }
    }
}
