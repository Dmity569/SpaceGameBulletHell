package com.example.spacegame;

import android.graphics.Canvas;
import android.graphics.Color;

public class LogicThread extends Thread{

    private volatile boolean running = true;//флаг для остановки потока

    public LogicThread(){
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
