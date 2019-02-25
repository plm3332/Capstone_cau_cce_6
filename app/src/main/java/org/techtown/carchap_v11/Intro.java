package org.techtown.carchap_v11;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.techtown.carchap_v11.R;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //임시로 mainactivity로 이동
                Intent intent = new Intent(Intro.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
