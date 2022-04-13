package com.example.spacegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
    public LevelActivity levelActivity;

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
                x = e.getX();
                y = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                logicThread.player.setPos(e.getX() - x, e.getY() - y);
                x = e.getX();
                if (x < 0)
                    x = 0;
                if (x > Resources.getSystem().getDisplayMetrics().widthPixels)
                    x = Resources.getSystem().getDisplayMetrics().widthPixels;
                y = e.getY();
                if (y < 0)
                    y = 0;
                if (y > Resources.getSystem().getDisplayMetrics().heightPixels)
                    y = Resources.getSystem().getDisplayMetrics().heightPixels;
                break;
            default:
                break;
        }
        return true;
    }


    public DrawView(Context context, LevelActivity level) {
        super(context);
        getHolder().addCallback(this);
        levelActivity = level;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        logicThread = new LogicThread(getContext());
        drawThread = new DrawThread(getContext(),getHolder());
        logicThread.start();
        drawThread.start();
        logicThread.drawThread = drawThread;
        logicThread.drawView = this;
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