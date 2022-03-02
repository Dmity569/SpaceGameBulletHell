package com.example.spacegame;

import android.content.Context;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {

    private DrawThread drawThread;
    private LogicThread logicThread;
    float x;
    float y;

    /*
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = 0;
        float y = 0;
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = e.getX();
                y = e.getY();
                Log.d("DEBUG", "Toch");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", Float.toString(x));
                Log.d("DEBUG", Float.toString(y));
                drawThread.setPos((float)(e.getX() - x), (float)(e.getY() - y));
                x = e.getX();
                y = e.getY();
                break;
            default:
                break;
        }
        return false;
    }

     */

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("DEBUG", "DOWN " + Float.toString(e.getX()));
                Log.d("DEBUG", "DOWN " + Float.toString(e.getY()));
                x = e.getX();
                y = e.getY();
                logicThread.player.health -= 10;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "MOVE " + Float.toString(e.getX()));
                Log.d("DEBUG", "MOVE " + Float.toString(e.getY()));
                logicThread.player.setPos(e.getX() - x, e.getY() - y);
                x = e.getX();
                y = e.getY();
                break;
            default:
                break;
        }
        return true;
    }


    public DrawView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        logicThread = new LogicThread(getContext());
        drawThread = new DrawThread(getContext(),getHolder());
        logicThread.start();
        drawThread.start();
        logicThread.drawThread = drawThread;
        drawThread.logicThread = logicThread;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // изменение размеров SurfaceView
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        logicThread.requestStop();
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                logicThread.join();
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
                //
            }
        }
    }
}