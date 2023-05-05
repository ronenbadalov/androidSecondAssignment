package com.example.a23b_11345b_l01.Logic;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class GameManager {

    private int carCurrentLane;
    private int dangerousCol;
    private int coinCol;
    private int life;
    private int crash;
    private int score;
    private int rows = 4;
    private int cols = 5;

    private int[][] boardState;

    public GameManager(int life) {
        this.life = life;
        this.carCurrentLane = 1;
        this.crash = 0;
        this.dangerousCol = -1;
        this.coinCol = -1;
        this.score = 0;
        this.boardState = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.boardState[i][j] = 0;
            }
        }
    }

    public int getCrash() {
        return crash;
    }

    public boolean isLose() {
        return life == crash;
    }

    public void resetGame() {
        crash = 0;
    }
    public int getCarCurrentLane() {
        return carCurrentLane;
    }

    public void setDangerousCol(int dangerCol){
        this.dangerousCol = dangerCol;
    }

    public int getCoinCol() {
        return coinCol;
    }

    public void setCoinCol(int coinCol) {
        this.coinCol = coinCol;
    }

    public void setCarCurrentLane(int newLane){
        carCurrentLane = newLane;
    }
    public void isCrashed(Context context, Vibrator v) {
        if (dangerousCol == carCurrentLane) { //Correct answer
            crash++;
            if(crash < life){
                Toast.makeText(context, String.format("🚨 Carefull! you have only %d lives left.", life - crash), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, String.format("YOU LOSE! 👎"), Toast.LENGTH_SHORT).show();
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
    }

    public void isRewarded(Context context, Vibrator v) {
        if (coinCol == carCurrentLane) { //Correct answer
                Toast.makeText(context, String.format("You got some coins! 🤑 +5 points", life - crash), Toast.LENGTH_SHORT).show();
                score += 5;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
    }

    public int getScore(){
        return score;
    }
    public void setScore(int newScore){
        score = newScore;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
    public int[][] getBoardState(){
        return boardState;
    }
    public void setBoardState(int[][] newBoardState){
        this.boardState = newBoardState;
    }

}
