package com.mrhi.spindragon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameoverActivity extends AppCompatActivity {

    ImageView iv;
    TextView tvChampion;
    TextView tvYourScore;

    boolean isChampion=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        iv= findViewById(R.id.iv);
        tvChampion= findViewById(R.id.tv_champion);
        tvYourScore= findViewById(R.id.tv_yourscore);

        Intent intent= getIntent();
        Bundle data= intent.getBundleExtra("Data");

        int score= data.getInt("Score", 0);
        int coin= data.getInt("Coin", 0);

        int yourscore= score + coin*10;

        String s= String.format("%07d", yourscore);
        tvYourScore.setText(s);

        if( yourscore > G.champion ){
            G.champion= yourscore;
            isChampion=true;
        }

        s= String.format("%07d", G.champion);
        tvChampion.setText(s);

        //챔피언 이미지가 있는가?
        if(G.championImg!=null){
            Uri uri= Uri.parse(G.championImg);
            iv.setImageURI(uri);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    void saveData(){
        SharedPreferences pref= getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();

        editor.putInt("Gem", G.gem);
        editor.putInt("Champion", G.champion);

        editor.putBoolean("Music", G.isMusic);
        editor.putBoolean("Sound", G.isSound);
        editor.putBoolean("Vibrate", G.isVibrate);

        editor.putString("ChampionImg", G.championImg);

        editor.commit();
    }

    public void clickImg(View v){
        if( !isChampion ) return;

        //디바이스의 사진을 선택하도록...
        //Gallery앱 or 사진앱을 실행!!!
        Intent intent= new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);

    }

    //startActivityForResult()로 실행한 액티비티가 종료되면
    //자동으로 실행되는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch ( requestCode ){
            case 10:
                if(resultCode==RESULT_OK){

                    Uri uri= data.getData();
                    if( uri != null){
                        Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
                        G.championImg= uri.toString();

                        iv.setImageURI(uri);
                    }else{
                        Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
                        Bundle bundle= data.getExtras();
                        Bitmap bm= (Bitmap)bundle.get("data");

                        iv.setImageBitmap(bm);
                    }

                }

                break;
        }


    }

    public void clickRetry(View v){
        Intent intent= new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickExit(View v){
        finish();
    }
}



















