package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;

class Player {
    float x;
    float y;
    int max_health;
    int health;
    Bitmap sprite;
    public Player(float pos_x, float pos_y, int hp, Bitmap bitmap) {
        x = pos_x;
        y = pos_y;
        max_health = hp;
        health = hp;
        sprite = bitmap;
    }
    public void setPos(float px, float py) {
        x += px;
        y += py;
    }
}

public class LogicThread extends Thread{

    public DrawThread drawThread;
    public Player player;
    private volatile boolean running = true;//флаг для остановки потока



    public LogicThread(Context context){
        player = new Player(450, 1400, 100, BitmapFactory.decodeResource(context.getResources(), R.drawable.player_texture));
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {

                Thread.sleep(10);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }
}
