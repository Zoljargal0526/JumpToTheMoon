package com.example.jumptothemoon;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class GameView extends View {
    private int charX, charY;
    private int metX=1000,metY,metSpeed;
    private int jumpSpeed = 8;
    private int canvasWidth, canvasHeight;
    private int y,m=0;
    private int score=0,k=0;
    private boolean up=true,touch=false;
    private boolean metiorRunning=true;
    public int[][] metiors = new int[15][3];
    private Bitmap character[] = new Bitmap[4];
    private Bitmap metior[]=new Bitmap[4];
    private Bitmap backgroundImage;
    private Bitmap GameO, resizedBitmap;
    boolean isDied=false;
    private Paint scorePaint = new Paint();
    private int leftOrRightSide;
    private int windowWidth=720,windowHeight=1280;
    public GameView(Context context) {
        super(context);
        metior[0]=BitmapFactory.decodeResource(getResources(),R.drawable.metior1);
        metior[1]=BitmapFactory.decodeResource(getResources(),R.drawable.metior2);
        metior[2]=BitmapFactory.decodeResource(getResources(),R.drawable.metior3);
        metior[3]=BitmapFactory.decodeResource(getResources(),R.drawable.metior4);

        character[0] = BitmapFactory.decodeResource(getResources(), R.drawable.character1);
        character[1] = BitmapFactory.decodeResource(getResources(), R.drawable.character2);
        character[2] = BitmapFactory.decodeResource(getResources(), R.drawable.character3);
        character[3] = BitmapFactory.decodeResource(getResources(), R.drawable.character4);

        canvasWidth= Resources.getSystem().getDisplayMetrics().widthPixels;
        canvasHeight=Resources.getSystem().getDisplayMetrics().heightPixels;
        charX=canvasWidth/2-100;
        charY=canvasHeight-300;
        y=charY;
        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background1);
        resizedBitmap = Bitmap.createScaledBitmap(backgroundImage, windowWidth  , windowHeight, false);
        GameO=BitmapFactory.decodeResource(getResources(),R.drawable.lost2);
        GameO=Bitmap.createScaledBitmap(GameO,windowWidth,windowHeight,false);
        scorePaint.setColor(Color.RED);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isDied) {
            canvas.drawBitmap(resizedBitmap, 0, 0, null);
            canvas.drawText("Score : " + score, 20, 60, scorePaint);
            //chuluu gargah mun hudluh hudulguun
            if (!metiorRunning) {
                m = (int) Math.floor(Math.random() * 4);
                metSpeed = (int) Math.floor((Math.random() * 5) + 2);
                leftOrRightSide = (int) Math.floor(Math.random()*2);
                System.out.println(leftOrRightSide);
                if(leftOrRightSide==0) {
                    metX = canvasWidth;
                }else{
                    metX = -200;
                    metSpeed = (-metSpeed);
                }
                metiorRunning = true;
            } else if (metX + 210 < 0 || canvasWidth + 50 < metX) {
                metiorRunning = false;
            } else {
                metY = charY + 128;
                canvas.drawBitmap(metior[m], metX, metY, null);
                metX = metX - metSpeed;
            }

            //deeshe ahih uy
            int upHeight=60;
            if (charY < canvasHeight / 2 - 100 && touch == false) {
                for (int i = 0; i < 15; i++) {
                    if (metiors[i][1] > 100) {
                        metiors[i][1] = metiors[i][1] + upHeight;
                    }
                }
                charY = charY + upHeight;
            }
            for (int i = 0; i < 15; i++) {
                if (metiors[i][1] > 100) {
                    canvas.drawBitmap(metior[metiors[i][2]], metiors[i][0], metiors[i][1], null);
                }
            }
            //character usreh uy
            if (touch) {
                if (up) {
                    if (charY - y <= 80) {
                        canvas.drawBitmap(character[0], charX, y, null);
                    } else if (charY - y <= 160 && charY - y > 80) {
                        canvas.drawBitmap(character[1], charX, y, null);
                    } else if (charY - y <= 240 && charY - y > 160) {
                        canvas.drawBitmap(character[2], charX, y, null);
                    } else if (charY - y <= 320 && charY - y > 240) {
                        canvas.drawBitmap(character[3], charX, y, null);
                    } else {
                        up = false;
                    }
                    y = y - jumpSpeed;
                } else {
                    if (charY - y <= 80 && charY - y >= 0) {
                        canvas.drawBitmap(character[0], charX, y, null);
                    } else if (charY - y <= 160 && charY - y > 80) {
                        canvas.drawBitmap(character[1], charX, y, null);
                    } else if (charY - y <= 240 && charY - y > 160) {
                        canvas.drawBitmap(character[2], charX, y, null);
                    } else if (charY - y > 240) {
                        canvas.drawBitmap(character[3], charX, y, null);
                    } else {
                        touch = false;
                        up = true;
                    }
                    y = y + jumpSpeed;
                }
            } else {
                canvas.drawBitmap(character[0], charX, charY, null);
                y = charY;
            }
            //chuluun deer gishgesen esehee shalgah uy
            if (metX +128 > charX && y + 256 == metY && metX < charX + 115) {
                    metiors[k][0] = metX;
                    metiors[k][1] = metY;
                    metiors[k][2] = m;
                    if (k < 14) {
                        k++;
                    } else {
                        k = 0;
                    }
                    score = score + 10;
                    charY=y;
                    y = charY;
                    touch = false;
                    metiorRunning = false;
            }else if(y+247>metY&&metX<charX+115&&metX+128>charX){
                isDied=true;
            }
        }else{
            canvas.drawBitmap(GameO,0,0,null);
            canvas.drawText("Your Score:"+score,180,canvasHeight-450,scorePaint);
        }
        }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touch=true;
        return true;
    }
}
