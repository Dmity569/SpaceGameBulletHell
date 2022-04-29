package com.example.spacegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.spacegame.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;

public class ShipsActivity extends AppCompatActivity {
    SharedPreferences mSettings;
    SharedPreferences shopData;

    TextView moneyShips;
    TextView shipInfo;
    ImageView shipImage;
    LinearLayout shipButtons;

    Button buy_choose_ships;
    View.OnClickListener buy = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    View.OnClickListener select = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };
    // EditorInfo.ShipType shipType;
    String infoText = "ShipInfo";

    //Ship Stat Bloc
    String ship_name = "Arrow";
    float max_health = EditorInfo.Ship_Arrow.max_health;
    float def = EditorInfo.Ship_Arrow.def;
    float e_def = EditorInfo.Ship_Arrow.e_def;
    int sprite = EditorInfo.Ship_Arrow.sprite;
    int price = EditorInfo.Ship_Arrow.price;


    private void changeShips() {
        infoText = "   Ship Info";
        infoText += "\nHull (Health):\n" + Float.toString(max_health);
        infoText += "\nArmour (Defence):\n" + Float.toString(def);
        infoText += "\nShield (Energy Def):\n" + Float.toString(e_def);
        shipInfo.setText(infoText);
        shipImage.setImageResource(sprite);
        if (shopData.getBoolean(ship_name, false)) {
            buy_choose_ships.setText("Buy for " + price + "G");
            buy_choose_ships.setOnClickListener(buy);
        }
        else {
            buy_choose_ships.setText("Select");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ships);
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        shopData = getSharedPreferences("shopData", Context.MODE_PRIVATE);
        moneyShips = (TextView) findViewById(R.id.moneyShips);
        moneyShips.setText(Integer.toString(mSettings.getInt("money", 0)) + "   G");
        shipInfo = (TextView) findViewById(R.id.shipInfo);
        shipImage = (ImageView) findViewById(R.id.shipImage);
        shipButtons = (LinearLayout) findViewById(R.id.shipButtons);
        buy_choose_ships = (Button) findViewById(R.id.buy_choose_ship);
        changeShips();
        for (int i = 0; i < EditorInfo.ship_amount; i++){
            Button ship_button = (Button) shipButtons.getChildAt(i);
            ship_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch ((String) ship_button.getText()){
                        case "Arrow":
                            max_health = EditorInfo.Ship_Arrow.max_health;
                            def = EditorInfo.Ship_Arrow.def;
                            e_def = EditorInfo.Ship_Arrow.e_def;
                            sprite = EditorInfo.Ship_Arrow.sprite;
                            price = EditorInfo.Ship_Arrow.price;
                            break;
                        case "Pure\nTrident":
                            max_health = EditorInfo.Ship_Pure_Trident.max_health;
                            def = EditorInfo.Ship_Pure_Trident.def;
                            e_def = EditorInfo.Ship_Pure_Trident.e_def;
                            sprite = EditorInfo.Ship_Pure_Trident.sprite;
                            price = EditorInfo.Ship_Pure_Trident.price;
                            break;
                    }
                    ship_name = (String) ship_button.getText();
                    changeShips();
                }
            }
                );
        }
    }
}
