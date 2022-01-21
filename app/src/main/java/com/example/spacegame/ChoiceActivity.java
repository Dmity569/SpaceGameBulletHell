package com.example.spacegame;

import android.content.Context;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                    }
                });
            }
        }
    }
}
