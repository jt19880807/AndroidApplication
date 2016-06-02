package com.devinjiang.androidapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView imageView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView= (ImageView) findViewById(R.id.iv);
        imageView1= (ImageView) findViewById(R.id.iv1);
        Bitmap bmpBuffer=Bitmap.createBitmap(500,800,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmpBuffer);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        int bmpwidth=bitmap.getWidth();
        int btmheight=bitmap.getHeight();
        Rect src=new Rect(0,0,bmpwidth,btmheight);
        Rect dst=new Rect(0,btmheight,bmpwidth*3,btmheight*3+btmheight);
        canvas.drawBitmap(bitmap,src,dst,null);
        imageView.setImageBitmap(bmpBuffer);
        imageView1.setImageBitmap(bitmap);
    }
}
