package com.example.spacegame;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback, View.OnTouchListener {

    private DrawThread drawThread;

    @Override
    public boolean onTouch(View view, MotionEvent e) {
        float x = e.getX();
        float y = e.getY();
        Log.d("DEBUG", "touch");
        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d("DEBUG", "move");
                drawThread.setPos((int)(e.getX() - x), (int)(e.getY() - y));
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