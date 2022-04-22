package com.example.spacegame;

public class EditorInfo {
    static int ship_amount = 1;
    public static class ShipType {
        float max_health;
        float def;
        float e_def;
    }
    public static class Ship_Arrow extends ShipType{
        float max_health = 100;
        float def = 10;
        float e_def = 10;
    }
}
