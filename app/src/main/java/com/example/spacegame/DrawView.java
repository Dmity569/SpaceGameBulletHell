package com.example.spacegame;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        Log.d("DEBUG", "Toch");
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", Integer.toString((int)(e.getX() - x)));
                Log.d("DEBUG", Integer.toString((int)(e.getY() - y)));
                drawThread.setPos((int)(e.getX() - x), (int)(e.getY() - y));
                x = e.getX();
                y = e.getY();
                break;
            default:
                break;
        }
        return false;
    }


    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(),getHolder());
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // изменение размеров SurfaceView
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }
}