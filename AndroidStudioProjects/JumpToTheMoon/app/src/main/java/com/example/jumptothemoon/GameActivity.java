package com.example.jumptothemoon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    private Handler handler=new Handler();
    private final static long interval=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView=new GameView(this);
        setContentView(gameView);
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gameView.invalidate();
                        if(gameView.isDied){
                            try {
                                Intent i=new Intent(GameActivity.this,MainActivity.class);
                                startActivity(i);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        },0, interval);

    }
}