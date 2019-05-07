package org.techtown.carchap_v11;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.techtown.carchap_v11.R;

public class Intro extends AppCompatActivity {
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //임시로 mainactivity로 이동
                Intent intent = new Intent(Intro.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }*/
@Override
protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intro);

    boolean isRunIntro = getIntent().getBooleanExtra("intro", true);
    if(isRunIntro) {
        beforeIntro();
        ImageView imageView=(ImageView)findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
    } else {
        afterIntro(savedInstanceState);
    }
}

    // 인트로 화면
    private void beforeIntro() {
        // 약 2초간 인트로 화면을 출력.
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Intro.this, Intro.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("intro", false);
                startActivity(intent);
                ImageView imageView=(ImageView)findViewById(R.id.imageView);
                imageView.setVisibility(View.INVISIBLE);
                // 액티비티 이동시 페이드인/아웃 효과를 보여준다. 즉, 인트로
                //    화면에 부드럽게 사라진다.
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        }, 2000);
    }

    // 인트로 화면 이후.
    private void afterIntro(Bundle savedInstanceState) {
        // 기본 테마를 지정한다.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //임시로 mainactivity로 이동
                Intent intent = new Intent(Intro.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }


}
