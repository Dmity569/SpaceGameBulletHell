package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Bitmap player;
    private Bitmap icon_health;
    private Bitmap bar_health;
    float x = 450;
    float y = 1400;
    int max_health = 100;
    int health = max_health;

    public void setPos(float px, float py) {
        x = px-60;
        y = py-100;
        health -= 10;
    }

    private volatile boolean running = true;//флаг для остановки потока

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        player = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_texture);
        icon_health = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_health);
        bar_health = BitmapFactory.decodeResource(context.getResources(), R.drawable.bar_health);
        this.surfaceHolder = surfaceHolder;
    }

    public void requestStop() {
        running = false;
    }

    public void drawUI(Canvas c, Paint p){
        Paint health_paint = new Paint();
        health_paint.setColor(Color.RED);
        c.drawRect(210, 0, 210 + 500 * health / max_health, 110, health_paint);
        c.drawBitmap(icon_health, 0, 0, p);
        c.drawBitmap(bar_health, 210, 0, p);
    }
    @Override
    public void run() {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawColor(Color.parseColor("#2e222e"));
//
//                    canvas.drawCircle(x, y, 50, paint);
                    canvas.drawBitmap(player, x, y, paint);
                    drawUI(canvas, paint);
                    Thread.sleep(10);
                    // рисование на canvas
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}