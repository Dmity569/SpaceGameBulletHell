package com.example.spacegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;

import androidx.annotation.RequiresApi;

public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Bitmap icon_health;
    private Bitmap bar_health;
    private Bitmap icon_gold;
    private Bitmap background;
    private int bg_y1, bg_y2, bg_y3;
    public LogicThread logicThread;
    public Typeface pixel_tf;


    private volatile boolean running = true;//флаг для остановки потока

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        icon_health = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_health);
        bar_health = BitmapFactory.decodeResource(context.getResources(), R.drawable.bar_health);
        icon_gold= BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_gold);
        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background_level);
        pixel_tf = Typeface.createFromAsset(context.getAssets(), "fonts/gorgeouspixel.ttf");
        this.surfaceHolder = surfaceHolder;
    }

    public void requestStop() {
        running = false;
    }

    public void drawBG(Canvas c, Paint p){
        c.drawColor(Color.parseColor("#2e222e"));
        c.drawBitmap(background, 0, bg_y1, p);
        c.drawBitmap(background, 0, bg_y2, p);
        c.drawBitmap(background, 0, bg_y3, p);
        c.drawBitmap(background, background.getWidth(), bg_y1, p);
        c.drawBitmap(background, background.getWidth(), bg_y2, p);
        c.drawBitmap(background, background.getWidth(), bg_y3, p);
        c.drawBitmap(background, 2 * background.getWidth(), bg_y1, p);
        c.drawBitmap(background, 2 * background.getWidth(), bg_y2, p);
        c.drawBitmap(background, 2 * background.getWidth(), bg_y3, p);
        if (bg_y1 >= Resources.getSystem().getDisplayMetrics().heightPixels)
            bg_y1 -= 3 * background.getHeight();
        if (bg_y2 >= Resources.getSystem().getDisplayMetrics().heightPixels)
            bg_y2 -= 3 * background.getHeight();
        if (bg_y3 >= Resources.getSystem().getDisplayMetrics().heightPixels)
            bg_y3 -= 3 * background.getHeight();

    }
    public void drawUI(Canvas c, Paint p){
        Paint health_paint = new Paint();
        health_paint.setColor(Color.RED);
        Paint text_paint = new Paint();
        text_paint.setColor(Color.WHITE);
        text_paint.setTextSize(72);
        text_paint.setTypeface(pixel_tf);
        c.drawRect(icon_health.getWidth(), 0, icon_health.getWidth() + 500 * logicThread.player.health / logicThread.player.max_health, bar_health.getHeight(), health_paint);
        c.drawBitmap(icon_health, 0, 0, p);
        c.drawBitmap(bar_health, icon_health.getWidth(), 0, p);
        c.drawBitmap(icon_gold, Resources.getSystem().getDisplayMetrics().widthPixels - icon_gold.getWidth(), 0, p);
        c.drawText(Integer.toString(logicThread.player.money) + " C",
                Resources.getSystem().getDisplayMetrics().widthPixels - icon_gold.getWidth(),
                text_paint.getTextSize() + icon_gold.getHeight(), text_paint);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        bg_y1 = -background.getHeight();
        bg_y2 = 0;
        bg_y3 = background.getHeight();
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                        drawBG(canvas, paint);
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
                        logicThread.player.item_list.forEach((n) ->
                                canvas.drawBitmap(n.sprite, n.x - n.sprite.getWidth() / 2, n.y - n.sprite.getHeight() / 2, paint));
                        canvas.drawBitmap(logicThread.player.sprite, logicThread.player.x - logicThread.player.sprite.getWidth() / 2,
                                logicThread.player.y - logicThread.player.sprite.getHeight() / 2, paint);
                        drawUI(canvas, paint);
                        if (!logicThread.gameover) {
                            bg_y1 += 1;
                            bg_y2 += 1;
                            bg_y3 += 1;
                        }
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