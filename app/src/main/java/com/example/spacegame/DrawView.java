package com.example.spacegame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class DrawView extends SurfaceView implements SurfaceHolder.Callback {
    SharedPreferences mSettings;
    private DrawThread drawThread;
    private LogicThread logicThread;
    float x;
    float y;
    public LevelActivity levelActivity;

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        mSettings = getContext().getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        if (!mSettings.getBoolean("альтернативное_управление", false) && !logicThread.gameover) {
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
        drawThread = new DrawThread(getContext(), getHolder());
        logicThread.start();
        drawThread.start();
        logicThread.drawThread = drawThread;
        logicThread.drawView = this;
        drawThread.logicThread = logicThread;

        SensorManager sensormanager;
        Sensor sensor;
        SensorEventListener sensorEventListener;

        sensormanager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensormanager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                mSettings = getContext().getSharedPreferences("mysettings", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSettings.edit();
                if (mSettings.getBoolean("альтернативное_управление", false) && !logicThread.gameover){
                    float[] rotatinMatrix = new float[16];
                    SensorManager.getRotationMatrixFromVector(rotatinMatrix, sensorEvent.values);

                    float[] remappedRotationMatrix = new float[16];
                    SensorManager.remapCoordinateSystem(rotatinMatrix,
                            SensorManager.AXIS_X,
                            SensorManager.AXIS_Y,
                            remappedRotationMatrix);

                    float[] degrees_x_y_z = new float[3];
                    SensorManager.getOrientation(remappedRotationMatrix, degrees_x_y_z);
                    degrees_x_y_z[0] = (float) (Math.toDegrees(degrees_x_y_z[0]));
                    degrees_x_y_z[1] = (float) (Math.toDegrees(degrees_x_y_z[1]));
                    degrees_x_y_z[2] = (float) (Math.toDegrees(degrees_x_y_z[2]));

                    x = degrees_x_y_z[2];
                    y = degrees_x_y_z[1];
                    logicThread.player.setPos((int)(x), (int)(-y));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensormanager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_FASTEST);
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