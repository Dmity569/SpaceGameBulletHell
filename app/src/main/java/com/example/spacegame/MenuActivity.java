package com.example.spacegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_menu);
        Button play = (Button) findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, ChoiceActivity.class);
                startActivity(i);
            }
        });

        Button creators = findViewById(R.id.creators);
        creators.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, CreatorsActivity.class);
                startActivity(i);
            }
        });

        Button settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, SettinsActivity.class);
                startActivity(i);
            }
        });



//        setContentView(new MyDraw(this));
    }
}

//class MyDraw extends View {
//    public MyDraw (Context context) {
//        super(context);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        int blue = Color.BLUE;
//        Paint paint = new Paint();
//        // Выводим изображение
//        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.spase2);
//        int xx = canvas.getWidth(), yy = canvas.getHeight();
//        canvas.drawBitmap(image, xx - image.getWidth(), yy - image.getHeight(), paint);
//    }
//}