package com.example.spacegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

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
    int t = 0;
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
        t += 1;
        if (t % 20 == 0)
            proj_list.add(new Green_Laser(x, y - 100, context));
        if (t % 80 == 0)
            proj_list.add(new Blue_Laser(x, y - 100, context));
        if (t % 20 == 0)
            proj_list.add(new Red_Laser(x, y - 100, context));
        if (t % 20 == 0)
            proj_list.add(new Purple_Laser(x, y - 100, context));
        if (t == 160)
            t = 0;
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
    Random ran = new Random();
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
class Blue_Laser extends P_Projectile{
    public Blue_Laser(float pos_x, float pos_y, Context cxt) {
        super(pos_x, pos_y, cxt);
        damage = 30;
        speed = 3;
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_blue_laser);
    }

    public void action() {
        super.action();
    }
}
class Red_Laser extends P_Projectile{
    public Red_Laser(float pos_x, float pos_y, Context cxt) {
        super(pos_x, pos_y, cxt);
        damage = 3;
        speed = 10;
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_red_laser);
    }

    public void action() {
        super.action();
        x += (ran.nextFloat() - 0.5) * 50;
    }
}
class Purple_Laser extends P_Projectile{
    public Purple_Laser(float pos_x, float pos_y, Context cxt) {
        super(pos_x, pos_y, cxt);
        damage = 3;
        speed = 10;
        sprite = BitmapFactory.decodeResource(context.getResources(), R.drawable.projectile_purple_laser);
        angle = -135 + 90 * ran.nextInt(2);
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
                player.shoot();

                player.proj_list.forEach((n) -> n.update());
                player.proj_list.removeIf((n) -> (n.x < -10 || n.y < -10 || n.x >
                        Resources.getSystem().getDisplayMetrics().widthPixels + 10 ||
                        n.y > Resources.getSystem().getDisplayMetrics().heightPixels + 10));
                Thread.sleep(10);
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }
}
