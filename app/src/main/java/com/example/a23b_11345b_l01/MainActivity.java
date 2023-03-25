package com.example.a23b_11345b_l01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
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

    private int colSelectionInterval = 3000;
    private int obstacleProgressInterval = 1000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Define handler
        handler = new Handler();

        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);
        hideObstacles();
        refreshUI();

        setNavButonsClickListeners();
    }

    private void hideObstacles(){
        for (int i = 0; i < main_IMG_obstacles.length; i++)
            for (int j = 0; j<main_IMG_obstacles[i].length;j++)
                main_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
    }

    private void refreshUI() {
         if(gameManager.isLose()){
            // Loser Screen!
            openScoreScreen("Game Over!");
        } else {

             for (int i = 0; i < main_IMG_cars.length; i++)
                 main_IMG_cars[i].setVisibility(gameManager.getCarCurrentLane() == i ?View.VISIBLE :  View.INVISIBLE);
            if (gameManager.getCrash() != 0)
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getCrash()].setVisibility(View.INVISIBLE);
        }
    }

    private void openScoreScreen(String status) {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(ScoreActivity.KEY_STATUS,status);
        startActivity(intent);
        finish();
    }

    private void setNavButonsClickListeners() {
        for (MaterialButton mb: main_nav_BTNS) {
            mb.setOnClickListener(v -> clicked(mb.getId()));
        }
    }

    private void clicked(int btnId) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        int currLane = gameManager.getCarCurrentLane();
        if(btnId == main_nav_BTNS[0].getId()){
            if(currLane == 0) currLane = 2;
            else currLane --;
        }else{
            if(currLane == 2) currLane = 0;
            else currLane ++;
        }
        gameManager.setCarCurrentLane(currLane);
        refreshUI();
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