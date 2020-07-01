package com.example.vegitable_delivery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN=5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
              /*  Pair[] pairs = new  Pair[2];
                pairs[0] = new Pair<View,String>(imageView,"logo_image");
                pairs[1] = new Pair<View,String>(logon,"logo_text");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Splash_screen.this,pairs);
                startActivity(intent,options.toBundle());*/
              startActivity(intent);
              finish();
            }

        },SPLASH_SCREEN);

    }
}
