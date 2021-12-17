package com.example.spacegame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
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