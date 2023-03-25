package com.example.a23b_11345b_l01.Logic;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

public class GameManager {

    private int carCurrentLane;
    private int dangerousCol;
    private int life;
    private int crash;

    public GameManager(int life) {
        this.life = life;
        this.carCurrentLane = 1;
        this.crash = 0;
        this.dangerousCol = -1;
    }

    public int getCrash() {
        return crash;
    }

    public boolean isLose() {
        return life == crash;
    }

    public int getCarCurrentLane() {
        return carCurrentLane;
    }

    public void setDangerousCol(int dangerCol){
        dangerousCol = dangerCol;
    }

    public void setCarCurrentLane(int newLane){
        carCurrentLane = newLane;
    }
    public void isCrashed(Context context, Vibrator v) {
        if (dangerousCol == carCurrentLane) { //Correct answer
            crash++;
            Toast.makeText(context, String.format("🚨 Carefull! you have only %d lives left.", crash - life), Toast.LENGTH_SHORT).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
    }
}
