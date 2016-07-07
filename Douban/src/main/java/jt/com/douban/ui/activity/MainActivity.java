package jt.com.douban.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import jt.com.douban.R;
import jt.com.douban.ui.utils.FastBlur;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView record;
    private ImageButton ibtnPlay;
    private boolean isPlaying=false;
    private Animator animator;
    private ImageView backgroundImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        record=(ImageView) findViewById(R.id.record);
        ibtnPlay= (ImageButton) findViewById(R.id.ibtn_play);
        backgroundImg=(ImageView)findViewById(R.id.backgroundImg);
        animator= AnimatorInflater.loadAnimator(MainActivity.this,R.animator.record_animator);
        animator.setTarget(record);
        ibtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isPlaying){
                    animator.pause();
                    isPlaying=false;
                    ibtnPlay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_play_72px));
                }
                else {
                    if(animator.isPaused()){
                        animator.resume();
                    }else {
                        animator.start();
                    }
                    isPlaying=true;
                    ibtnPlay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pause_72px));
                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.ibtn_play:
                Log.d("TAG","ibtnPlay.click");
                Animator animator= AnimatorInflater.loadAnimator(this,R.animator.record_animator);
                animator.setTarget(record);
                break;
        }
    }
}
