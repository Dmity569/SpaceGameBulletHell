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
    SharedPreferences.Editor settingsEditor;

    SharedPreferences shopData;
    SharedPreferences.Editor shopEditor;

    TextView moneyShips;
    TextView shipInfo;
    ImageView shipImage;
    LinearLayout shipButtons;

    Button buy_choose_ships;
    View.OnClickListener buy = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mSettings.getInt("money", 0) >=  price) {
                settingsEditor.putInt("money", mSettings.getInt("money", 0) - price);
                settingsEditor.putString("selectedShip", ship_name);
                shopEditor.putBoolean(ship_name, true);
                settingsEditor.apply();
                shopEditor.apply();
                moneyShips.setText(Integer.toString(mSettings.getInt("money", 0)) + "    G");
                changeShips();
            }
        }
    };
    View.OnClickListener select = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            settingsEditor.putString("selectedShip", ship_name);
            settingsEditor.apply();
            changeShips();
        }
    };

    View.OnClickListener selected = new View.OnClickListener() {
        @Override
        public void onClick(View view) {}
    };
    // EditorInfo.ShipType shipType;
    String infoText = "ShipInfo";

    //Ship Stat Bloc
    String ship_name;
    float max_health;
    float def;
    float e_def;
    int sprite;
    int price;

    private void setShip(String name) {
        switch (name){
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
            case "Glass\nCannon":
                max_health = EditorInfo.Ship_Glass_Cannon.max_health;
                def = EditorInfo.Ship_Glass_Cannon.def;
                e_def = EditorInfo.Ship_Glass_Cannon.e_def;
                sprite = EditorInfo.Ship_Glass_Cannon.sprite;
                price = EditorInfo.Ship_Glass_Cannon.price;
                break;
        }
        ship_name = name;
        changeShips();
    }
    private void changeShips() {
        infoText = "   Ship Info";
        infoText += "\nHull (Health):\n" + Float.toString(max_health);
        infoText += "\nArmour (Defence):\n" + Float.toString(def);
        infoText += "\nShield (Energy Def):\n" + Float.toString(e_def);
        shipInfo.setText(infoText);
        shipImage.setImageResource(sprite);

        if (mSettings.getString("selectedShip", "").equals(ship_name)) {
            buy_choose_ships.setText("Selected");
            buy_choose_ships.setOnClickListener(selected);
        }
        else if (shopData.getBoolean(ship_name, false)) {
            buy_choose_ships.setText("Select");
            buy_choose_ships.setOnClickListener(select);
        }
        else {
            buy_choose_ships.setText("Buy for " + price + " G");
            buy_choose_ships.setOnClickListener(buy);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_ships);
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        settingsEditor = mSettings.edit();
        shopData = getSharedPreferences("shopData", Context.MODE_PRIVATE);
        shopEditor = shopData.edit();
        moneyShips = (TextView) findViewById(R.id.moneyShips);
        moneyShips.setText(Integer.toString(mSettings.getInt("money", 0)) + "    G");
        shipInfo = (TextView) findViewById(R.id.shipInfo);
        shipImage = (ImageView) findViewById(R.id.shipImage);
        shipButtons = (LinearLayout) findViewById(R.id.shipButtons);
        buy_choose_ships = (Button) findViewById(R.id.buy_choose_ship);
        setShip(mSettings.getString("selectedShip", "Arrow"));
        for (int i = 0; i < EditorInfo.ship_amount; i++){
            Button ship_button = (Button) shipButtons.getChildAt(i);
            ship_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setShip((String) ship_button.getText());
                }
            }
                );
        }
    }
}
