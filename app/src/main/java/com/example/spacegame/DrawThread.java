package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Bitmap icon_health;
    private Bitmap bar_health;
    public LogicThread logicThread;


    private volatile boolean running = true;//флаг для остановки потока

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
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
        c.drawRect(210, 0, 210 + 500 * logicThread.player.health / logicThread.player.max_health, 110, health_paint);
        c.drawBitmap(icon_health, 0, 0, p);
        c.drawBitmap(bar_health, 210, 0, p);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    if (logicThread.gameover == false) {
                        canvas.drawColor(Color.parseColor("#2e222e"));
//
//                    canvas.drawCircle(x, y, 50, paint);
                        logicThread.player.enemy_list.forEach((n) -> {
                            canvas.drawBitmap(n.sprite, n.x - n.sprite.getWidth() / 2, n.y - n.sprite.getHeight() / 2, paint);
                            paint.setColor(Color.RED);
                            canvas.drawRect(n.x - n.sprite.getWidth() / 2 - 5,
                                    n.y - n.sprite.getHeight() / 2 - 5, n.x + n.sprite.getWidth() / 2,
                                    n.y - n.sprite.getHeight() / 2, paint);
                            paint.setColor(Color.GREEN);
                            canvas.drawRect(n.x - n.sprite.getWidth() / 2 - 5,
                                    n.y - n.sprite.getHeight() / 2 - 5,
                                    n.x - n.sprite.getWidth() / 2 + (n.sprite.getWidth() * (n.health / n.max_health)),
                                    n.y - n.sprite.getHeight() / 2, paint);
                        });
                        logicThread.player.boss_list.forEach((n) -> {
                            canvas.drawBitmap(n.sprite, n.x - n.sprite.getWidth() / 2, n.y - n.sprite.getHeight() / 2, paint);
                            paint.setColor(Color.RED);
                            canvas.drawRect(n.x - n.sprite.getWidth() / 2 - 5,
                                    n.y - n.sprite.getHeight() / 2 - 5, n.x + n.sprite.getWidth() / 2,
                                    n.y - n.sprite.getHeight() / 2 + 15, paint);
                            paint.setColor(Color.GREEN);
                            canvas.drawRect(n.x - n.sprite.getWidth() / 2 - 5,
                                    n.y - n.sprite.getHeight() / 2 - 5,
                                    n.x - n.sprite.getWidth() / 2 + (n.sprite.getWidth() * (n.health / n.max_health)),
                                    n.y - n.sprite.getHeight() / 2 + 15, paint);
                        });
                        logicThread.player.proj_list.forEach((n) ->
                                canvas.drawBitmap(n.sprite, n.x - n.sprite.getWidth() / 2, n.y - n.sprite.getHeight() / 2, paint));
                        logicThread.player.e_proj_list.forEach((n) ->
                                canvas.drawBitmap(n.sprite, n.x - n.sprite.getWidth() / 2, n.y - n.sprite.getHeight() / 2, paint));
                        canvas.drawBitmap(logicThread.player.sprite, logicThread.player.x - logicThread.player.sprite.getWidth() / 2,
                                logicThread.player.y - logicThread.player.sprite.getHeight() / 2, paint);
                        drawUI(canvas, paint);
                        Thread.sleep(10);
                    }

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