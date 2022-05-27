package com.example.spacegame;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class EditorInfo {
    static int ship_amount = 3;
    public static class ShipType {
        static float max_health;
        static float def;
        static float e_def;
        static int sprite;
        static int price;
    }
    public static class Ship_Arrow extends ShipType{
        static float max_health = 100;
        static float def = 10;
        static float e_def = 10;
        static int sprite = R.drawable.ship_arrow;
        static int price = 0;
    }
    public static class Ship_Pure_Trident extends ShipType{
        static float max_health = 80;
        static float def = 20;
        static float e_def = 20;
        static int sprite = R.drawable.ship_pure_trident;
        static int price = 100;
    }
    public static class Ship_Glass_Cannon extends ShipType{
        static float max_health = 1;
        static float def = 1;
        static float e_def = 1;
        static int sprite = R.drawable.ship_glass;
        static int price = 369;
    }
}
