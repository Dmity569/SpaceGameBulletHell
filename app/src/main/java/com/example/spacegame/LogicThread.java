package com.example.spacegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.provider.MediaStore.Audio;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Hashtable;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;



class Player {
    float x;
    float y;
    int max_health;
    int health;
    Bitmap sprite;
    ArrayList<P_Projectile> proj_list = new ArrayList(50);
    Context context;
    public Player(float pos_x, float pos_y, int hp, Bitmap bitmap, Context cxt) {
        x = pos_x;
        y = pos_y;
        max_health = hp;
        health = hp;
        sprite = bitmap;
        context = cxt;
    }
    public void setPos(float px, float py) {
        x += px;
        y += py;
    }
    public void shoot(){
        proj_list.add(new Green_Laser(x, y - 100, context));
        Log.d("Test1", "Test2");
        MediaPlayer mp;
        mp = MediaPlayer.create(context.getApplicationContext(), R.raw.simple_shoot);
        mp.setLooping(true);
        mp.seekTo(0);
        mp.setVolume(0.5f,0.5f);
        mp.start();
        int totltime = mp.getDuration();
        Log.d("Test3", "Test4");

    }
}

class P_Projectile {
    float x;
    float y;
    int damage;
    float speed;
    float angle = -90;
    Bitmap sprite;
    Context context;
    public P_Projectile(float pos_x, float pos_y, Context cxt) {
        x = pos_x;
        y = pos_y;
        context = cxt;
    }
    public void action() {
        x += speed * cos(toRadians(angle));
        y += speed * sin(toRadians(angle));
    }

    public void update() {
        action();
    }
}
class Green_Laser extends P_Projectile{
    public Green_Laser(float pos_x, float pos_y, Context cxt) {
        super(pos_x, pos_y, cxt);
        damage = 3;
        speed = 10;
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_green_laser);
    }

    public void action() {
        super.action();
    }
}
public class LogicThread extends Thread{

    public DrawThread drawThread;
    public Player player;
    private volatile boolean running = true;//флаг для остановки потока



    public LogicThread(Context context){
        player = new Player(450, 1400, 100, BitmapFactory.decodeResource(context.getResources(), R.drawable.player_texture), context);
    }

    public void requestStop() {
        running = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        int t = 0;
        while (running) {
            try {
                if (t == 0)
                player.shoot();


                player.proj_list.forEach((n) -> n.update());
                player.proj_list.removeIf((n) -> (n.x < 0 || n.y < 0 || n.x >
                        Resources.getSystem().getDisplayMetrics().widthPixels ||
                        n.y > Resources.getSystem().getDisplayMetrics().heightPixels));
                t += 1;
                if (t == 20)
                    t = 0;
                Thread.sleep(10);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }
}
