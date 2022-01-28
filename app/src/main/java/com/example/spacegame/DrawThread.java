package com.example.spacegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.widget.Toast;

public class DrawThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Bitmap bitmap;
    int x = 450;
    int y = 1400;

    public void setPos(int px, int py) {
        x = x + px;
        y = y + py;
    }

    private volatile boolean running = true;//флаг для остановки потока

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_texture);
        this.surfaceHolder = surfaceHolder;
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawColor(Color.BLACK);
//
//                    canvas.drawCircle(x, y, 50, paint);
                    canvas.drawBitmap(bitmap, x, y, paint);
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