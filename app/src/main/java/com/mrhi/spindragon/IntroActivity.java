package com.mrhi.spindragon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    ImageView iv;

    //스케쥴관리 객체(비서객체)
    Timer timer= new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        iv= findViewById(R.id.iv);
        //View에게 애니메이션 효과를 주는 객체 생성
        //appear_logo.xml문서를 읽어서 Animation객체로 생성
        Animation ani= AnimationUtils.loadAnimation(this, R.anim.appear_logo);
        iv.startAnimation(ani);

        //4초후에 MainActivity 실행!!!!
        //스케쥴관리 객체에게 스케쥴 등록
        timer.schedule(task, 4000);

        //저장된 데이터들 로딩하기
        loadData();
    }

    void loadData(){
        SharedPreferences pref= getSharedPreferences("Data", MODE_PRIVATE);

        G.gem= pref.getInt("Gem", 0);
        G.champion= pref.getInt("Champion", 0);

        G.isMusic= pref.getBoolean("Music", true);
        G.isSound= pref.getBoolean("Sound", true);
        G.isVibrate= pref.getBoolean("Vibrate", true);

        G.championImg= pref.getString("ChampionImg", null);
    }

    //timer의 스케쥴링 작업을 수행하는 객체 생성
    TimerTask task= new TimerTask() {
        @Override
        public void run() {
            //스케쥴링에 의해 4초후에 이 메소드 실행
            Intent intent= new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);

            finish();

        }
    };

}
