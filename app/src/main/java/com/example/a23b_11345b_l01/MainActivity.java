package com.example.a23b_11345b_l01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;

import com.example.a23b_11345b_l01.Logic.GameManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    private MaterialButton[] main_nav_BTNS;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] main_IMG_cars;
    private ShapeableImageView[][] main_IMG_obstacles;
    private GameManager gameManager;
    private int obstacleProgressIntervalMS = 1000;
    private Handler ObstacleProgressHandler;
    private int tick = 0;

    private Runnable ObstacleProgressRunnable;

    private Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ObstacleProgressHandler = new Handler();
        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);
        hideObstacles();
        refreshUI();
        setNavButtonsClickListeners();
        initRunnable();
        obstacleProgress();
    }

    private void hideObstacles(){
        for (int i = 0; i < main_IMG_obstacles.length; i++)
            for (int j = 0; j<main_IMG_obstacles[i].length;j++)
                main_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
    }

    private void refreshUI() {
         if(gameManager.isLose()){
//            clearObstacleProgress();
             gameManager.resetGame();
             for (int i = 0; i < main_IMG_hearts.length; i++)
                 main_IMG_hearts[i].setVisibility(View.VISIBLE);
         }
             for (int i = 0; i < main_IMG_cars.length; i++)
                 main_IMG_cars[i].setVisibility(gameManager.getCarCurrentLane() == i ?View.VISIBLE :  View.INVISIBLE);
        gameManager.isCrashed(getApplicationContext(),v);
        if (gameManager.getCrash() != 0)
             main_IMG_hearts[main_IMG_hearts.length  - gameManager.getCrash()].setVisibility(View.INVISIBLE);
    }

    private void setNavButtonsClickListeners() {
        for (MaterialButton mb: main_nav_BTNS) {
            mb.setOnClickListener(v -> clicked(mb.getId()));
        }
    }

    private void clicked(int btnId) {
        int currLane = gameManager.getCarCurrentLane();
        if(btnId == main_nav_BTNS[0].getId()){
            currLane = (currLane + main_IMG_cars.length - 1) % main_IMG_cars.length;
        }else{
            currLane = ++currLane % main_IMG_cars.length;
        }
        gameManager.setCarCurrentLane(currLane);
        refreshUI();
    }

    private void initRunnable(){
        ObstacleProgressRunnable = new Runnable() {
            @Override
            public void run() {
                for (int colIndex = 0; colIndex < main_IMG_obstacles.length; colIndex++)
                    for (int rowIndex = main_IMG_obstacles[colIndex].length - 1; rowIndex>=0;rowIndex--) {
                        if(main_IMG_obstacles[colIndex][rowIndex].getVisibility() == View.VISIBLE) {
                            // move obstacle
                            main_IMG_obstacles[colIndex][rowIndex].setVisibility(View.INVISIBLE);
                            if (rowIndex != main_IMG_obstacles[colIndex].length - 1) {
                                main_IMG_obstacles[colIndex][rowIndex + 1].setVisibility(View.VISIBLE);
                            }
                            // if obstacle is on the last row, set it to be the dangerous col
                            if(rowIndex + 1 == main_IMG_obstacles[colIndex].length - 1){
                                gameManager.setDangerousCol(colIndex);
                                refreshUI();
                            }else{
                                gameManager.setDangerousCol(-1);
                            }
                        }
                    }

                // after 2 seconds add a new obstacle
                if(tick == 1){
                    tick = 0;
                    int colIndex = getRandomNumber(0,main_IMG_obstacles.length);
                    main_IMG_obstacles[colIndex][0].setVisibility(View.VISIBLE);
                }else{
                    tick++;
                }
            ObstacleProgressHandler.postDelayed(ObstacleProgressRunnable, obstacleProgressIntervalMS);
            }
        };
    }

    private void obstacleProgress() {
        ObstacleProgressHandler.postDelayed(ObstacleProgressRunnable, obstacleProgressIntervalMS);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void clearObstacleProgress() {
        ObstacleProgressHandler.removeCallbacks(ObstacleProgressRunnable);
    }

    private void findViews() {
        main_nav_BTNS = new MaterialButton[]{
            findViewById(R.id.main_nav_BTNS_left),
            findViewById(R.id.main_nav_BTNS_right)};
        main_IMG_hearts = new ShapeableImageView[]{
            findViewById(R.id.main_IMG_heart1),
            findViewById(R.id.main_IMG_heart2),
            findViewById(R.id.main_IMG_heart3)};

        main_IMG_cars = new ShapeableImageView[]{
            findViewById(R.id.main_car01),
            findViewById(R.id.main_car02),
            findViewById(R.id.main_car03)};

        main_IMG_obstacles = new ShapeableImageView[][]{
            {
                findViewById(R.id.main_row0_col0_obstacle),
                findViewById(R.id.main_row1_col0_obstacle),
                findViewById(R.id.main_row2_col0_obstacle),
                findViewById(R.id.main_row3_col0_obstacle),
            },
            {
                findViewById(R.id.main_row0_col1_obstacle),
                findViewById(R.id.main_row1_col1_obstacle),
                findViewById(R.id.main_row2_col1_obstacle),
                findViewById(R.id.main_row3_col1_obstacle),
            },
            {
                findViewById(R.id.main_row0_col2_obstacle),
                findViewById(R.id.main_row1_col2_obstacle),
                findViewById(R.id.main_row2_col2_obstacle),
                findViewById(R.id.main_row3_col2_obstacle),
            },
        };

    }
}