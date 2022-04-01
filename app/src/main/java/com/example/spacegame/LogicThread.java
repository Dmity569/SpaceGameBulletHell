package com.example.spacegame;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.provider.MediaStore.Audio;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.toRadians;

class Player {
    float x;
    float y;
    float max_health;
    float health;
    Bitmap sprite;
    ArrayList<Enemy> enemy_list = new ArrayList(50);
    ArrayList<Enemy> boss_list = new ArrayList(3);
    ArrayList<P_Projectile> proj_list = new ArrayList(50);
    ArrayList<E_Projectile> e_proj_list = new ArrayList(50);
    Context context;
    int t = 0;
    int cd = 0;
    float def = 10;
    float e_def = 1;
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
        // if (t % 20 == 0)
            // proj_list.add(new Green_Laser(x, y - 100, context));
        // if (t % 80 == 0)
            // proj_list.add(new Blue_Laser(x, y - 100, context));
        if (t % 80 == 0) {
            proj_list.add(new Red_Laser(x, y - 110, this));
            proj_list.add(new Red_Laser(x - 10, y - 90, this));
            proj_list.add(new Red_Laser(x + 10, y - 80, this));
            proj_list.add(new Red_Laser(x, y - 125, this));
            proj_list.add(new Red_Laser(x - 25, y - 100, this));
            proj_list.add(new Red_Laser(x + 25, y - 120, this));
        }
        // if (t % 20 == 0)
            // proj_list.add(new Purple_Laser(x, y - 100, context));
        if (t == 160)
            t = 0;
    }
}

class Entity {
    float x;
    float y;
    float speed;
    float angle = -90;
    Bitmap sprite;
    Player player;
    Random ran = new Random();
    public Entity(float pos_x, float pos_y, Player plr) {
        x = pos_x;
        y = pos_y;
        player = plr;
    }
    public void action() {
        x += speed * cos(toRadians(angle));
        y += speed * sin(toRadians(angle));
    }

    public void update() {
        action();
    }
}
class P_Projectile extends Entity {
    float atk;
    float e_atk;

    public P_Projectile(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update() {
        super.update();
        player.enemy_list.forEach((n) -> {
            if (LogicThread.distance(x, y, n.x + 50, n.y + 50) <= 50) {
                n.health -= ((atk / n.def) + (e_atk / n.e_def));
            }
        });
        player.boss_list.forEach((n) -> {
            if (LogicThread.distance(x, y, n.x + 50, n.y + 50) <= 50) {
                n.health -= ((atk / n.def) + (e_atk / n.e_def));
            }
        });

    }
}
class Green_Laser extends P_Projectile{
    public Green_Laser(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
        atk = 3;
        e_atk = 0;
        speed = 10;
        sprite = BitmapFactory.decodeResource(plr.context.getResources(), R.drawable.projectile_green_laser);
    }

    public void action() {
        super.action();
    }
}
class Blue_Laser extends P_Projectile{
    public Blue_Laser(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
        atk = 30;
        e_atk = 0;
        speed = 3;
        sprite = BitmapFactory.decodeResource(plr.context.getResources(), R.drawable.projectile_blue_laser);
    }

    public void action() {
        super.action();
    }
}
class Red_Laser extends P_Projectile{
    public Red_Laser(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
        atk = 3;
        e_atk = 0;
        speed = 10;
        sprite = BitmapFactory.decodeResource(plr.context.getResources(), R.drawable.projectile_red_laser);
    }

    public void action() {
        super.action();
        x += (ran.nextFloat() - 0.5) * 50;
    }
}
class Purple_Laser extends P_Projectile{
    public Purple_Laser(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
        atk = 9;
        e_atk = 0;
        speed = 10;
        sprite = BitmapFactory.decodeResource(plr.context.getResources(), R.drawable.projectile_purple_laser);
        angle = -135 + 90 * ran.nextInt(2);
    }

    public void action() {
        super.action();
    }
}

class Enemy extends Entity {
    float def = 1;
    float e_def = 1;
    float health = 30;
    float max_health = 30;
    float target_x = 0;
    float target_y = 0;
    int t = 0;
    boolean immortal = false;
    public Enemy(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
        angle = 90;
    }

    public void action() {
        super.action();
        shoot();
    }

    public void shoot() {
    }
}

class Pirate extends Enemy {
    public Pirate(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
        speed = 10;
        sprite = BitmapFactory.decodeResource(plr.context.getResources(), R.drawable.enemy_pure_trident);
        target_y = 100 + 50 * ran.nextInt(10);
    }
    @Override
    public void action() {
        super.action();
        if (y >= target_y)
            if (angle == 90)
                angle = 180 * ran.nextInt(2);
            else if (angle < 90 && x >= Resources.getSystem().getDisplayMetrics().widthPixels - 100) {
                angle = 175;
            }
            else if (angle >= 90 && x <= 0) {
                angle = 5;
            }
    }

    @Override
    public void shoot() {
        super.shoot();
        t += 1;
        if (t % 40 == 0) {
            player.e_proj_list.add(new Death_Laser(x, y, player, 65));
            player.e_proj_list.add(new Death_Laser(x, y, player, 90));
            player.e_proj_list.add(new Death_Laser(x, y, player, 115));
        }
        if (t == 160)
            t = 0;
    }
}


class Death_Skull extends Enemy {

    public Death_Skull(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
        def = 20;
        e_def = 20;
        speed = 1;
        health = 350;
        max_health = 350;
        sprite = BitmapFactory.decodeResource(plr.context.getResources(), R.drawable.boss_death_skull);
        target_y = 200;
    }
    @Override
    public void action() {
        super.action();
        if (y >= target_y) {
            if (target_y == 200) {
                target_y = -100;
                speed = 3;
                angle = 0;
            }
            angle += 1;
            if (angle >= 180)
                angle -= 360;
        }
    }
}

class E_Projectile extends Entity {
    float atk = 1;
    float e_atk = 1;
    public E_Projectile(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
    }

    @Override
    public void update() {
        super.update();
        if (LogicThread.distance(x, y, player.x + 50, player.y + 50) <= 50) {
            player.health -= ((atk / player.def) + (e_atk / player.e_def));
        }
    }
}

class Bomb extends E_Projectile {
    public Bomb(float pos_x, float pos_y, Player plr) {
        super(pos_x, pos_y, plr);
    }
}
class Death_Laser extends E_Projectile {
    public Death_Laser(float pos_x, float pos_y, Player plr, int ang) {
        super(pos_x, pos_y, plr);
        atk = 10;
        e_atk = 0;
        speed = 12;
        angle = ang;
        sprite = BitmapFactory.decodeResource(plr.context.getResources(), R.drawable.projectile_green_laser);
    }
}
public class LogicThread extends Thread {

    public DrawThread drawThread;
    public Player player;
    private volatile boolean running = true;//флаг для остановки потока
    public SoundPool sp;
    public Dictionary<String, Integer> sounds = new Hashtable<>();
    public boolean gameover = false;

    public static double distance(float x1, float y1, float x2, float y2) {
        return sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }


    public LogicThread(Context context){
        sp = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
        sounds.put("laser", sp.load(context, R.raw.laser, 1));
        player = new Player(450, 1400, 100, BitmapFactory.decodeResource(context.getResources(), R.drawable.player_texture), context);
    }

    public void requestStop() {
        running = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run() {
        int t = 0;
        int spawns = 0;
        while (running) {
            try {
                if (gameover == false) {
                player.shoot();
                if (t % 80 == 0)
                    sp.play(sounds.get("laser"), 1, 1, 0, 0, 1);

                if (player.cd > 0)
                    player.cd -= 1;


                player.proj_list.forEach((n) -> n.update());
                player.e_proj_list.forEach((n) -> n.update());
                player.enemy_list.forEach((n) -> n.update());
                player.boss_list.forEach((n) -> n.update());

                player.proj_list.removeIf((n) -> (n.x < -10 || n.y < -10 || n.x >
                        Resources.getSystem().getDisplayMetrics().widthPixels + 10 ||
                        n.y > Resources.getSystem().getDisplayMetrics().heightPixels + 10));
                player.e_proj_list.removeIf((n) -> (n.x < -10 || n.y < -10 || n.x >
                        Resources.getSystem().getDisplayMetrics().widthPixels + 10 ||
                        n.y > Resources.getSystem().getDisplayMetrics().heightPixels + 10));
                player.enemy_list.removeIf((n) -> (n.health <= 0 ||
                        n.y > Resources.getSystem().getDisplayMetrics().heightPixels + 100));
                player.boss_list.removeIf((n) -> (n.health <= 0));

                t += 1;
                if (t == 100) {
                    t = 0;
                    if (player.enemy_list.size() <= 4 & spawns < 8) {
                        player.enemy_list.add(new Pirate(
                                Resources.getSystem().getDisplayMetrics().widthPixels / 2 - 50,
                                0, player));
                        spawns += 1;
                    }
                    else if (spawns == 8) {
                        player.boss_list.add(new Death_Skull(
                                Resources.getSystem().getDisplayMetrics().widthPixels / 2 - 100,
                                0, player));
                        spawns += 1;
                    }
                }
                if (player.health <= 0)
                    gameover = true;
                Thread.sleep(10);
                }
            }
            catch (Exception e) {
                    e.printStackTrace();
            }
        }
    }
}
