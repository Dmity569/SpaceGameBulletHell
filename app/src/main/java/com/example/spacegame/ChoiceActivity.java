package com.example.spacegame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;

public class ChoiceActivity extends AppCompatActivity {
    MediaPlayer mp;
    public SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);

        float volume = (float) mSettings.getInt("music", 50);

        mp = MediaPlayer.create(this, R.raw.menu);
        mp.setLooping(true);
        mp.seekTo(0);

        int MAX_VOLUME = 100;

        final float volume_x = (float) (1 - (Math.log(MAX_VOLUME - volume) / Math.log(MAX_VOLUME)));
        mp.setVolume(volume_x, volume_x);

        mp.start();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choice);
        int level_amount = 9;
        TableLayout table = (TableLayout) findViewById(R.id.level_table);
        Button[] level_array = new Button[level_amount];
        for (int a = 0; a < 3; a++) {
            TableRow row = (TableRow) table.getChildAt(a);
            LinearLayout row_layout = (LinearLayout) row.getChildAt(0);
            for (int b = 0; b < row_layout.getChildCount(); b++){
                int level_id = a * 3 + b;
                level_array[level_id] = (Button) row_layout.getChildAt(b);
                level_array[level_id].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(ChoiceActivity.this, LevelActivity.class);
                        i.putExtra("level_id", level_id);
                        startActivity(i);
                        mp.stop();
                    }
                });
            }
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();

    }
    @Override
    protected void onUserLeaveHint() {
        mp.stop();
        super.onUserLeaveHint();
    }
}
