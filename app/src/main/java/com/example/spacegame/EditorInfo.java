package com.example.spacegame;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class EditorInfo {
    static int ship_amount = 2;
    public static class ShipType {
        static float max_health;
        static float def;
        static float e_def;
        static int sprite;
    }
    public static class Ship_Arrow extends ShipType{
        static float max_health = 100;
        static float def = 10;
        static float e_def = 10;
        static int sprite = R.drawable.ship_arrow;
    }
    public static class Ship_Pure_Trident extends ShipType{
        static float max_health = 80;
        static float def = 20;
        static float e_def = 20;
        static int sprite = R.drawable.ship_pure_trident;
    }
}
