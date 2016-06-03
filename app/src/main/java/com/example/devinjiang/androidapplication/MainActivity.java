package com.example.devinjiang.androidapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mtextView;
    private Button mbtnStart;
    private Button mbtnEnd;
    private static final String TAG="ActivityDemo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        mbtnStart= (Button) findViewById(R.id.btnStart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d(TAG,"start onCreate---");
        mbtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"start onStart---");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"start onRestart---");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"start onResume---");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"start onPause---");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"start onStop---");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"start onDestroy---");
    }
}
