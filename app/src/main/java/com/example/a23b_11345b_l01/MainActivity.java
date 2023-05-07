package com.example.a23b_11345b_l01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.Glide;
import com.example.a23b_11345b_l01.Logic.GameManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {
    private MaterialButton[] main_nav_BTNS;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView[] main_IMG_cars;
    private ShapeableImageView[][] main_IMG_obstacles;
    private ShapeableImageView[][] main_IMG_coins;
    private MaterialTextView main_odometer_text;
    private GameManager gameManager;
    private int obstacleProgressIntervalMS;
    private Handler ObstacleProgressHandler;
    private int tick = 0;
    private Runnable ObstacleProgressRunnable;
    private Vibrator v;
    private MediaPlayer hitSound;
    private MediaPlayer coinSound;
    private Location currentLocation;
    public static final String KEY_NORMAL_SPEED = "KEY_NORMAL_SPEED";
    public static final String KEY_IS_SENSOR = "KEY_IS_SENSOR";
    public static final String KEY_LOCATION = "KEY_LOCATION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent previousIntent = getIntent();
        obstacleProgressIntervalMS = previousIntent.getBooleanExtra(KEY_NORMAL_SPEED,true) ? 1000 : 500;
        previousIntent.getBooleanExtra(KEY_IS_SENSOR,false);
        currentLocation = previousIntent.getParcelableExtra(KEY_LOCATION);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        ObstacleProgressHandler = new Handler();
        findViews();

        gameManager = new GameManager(main_IMG_hearts.length);
        hideObstaclesAndCoins();
        refreshUI();
        setNavButtonsClickListeners();
        initRunnable();
        obstacleProgress();
        hitSound = MediaPlayer.create(this,R.raw.oof);
        hitSound.setVolume(1.0f,1.0f);
        coinSound = MediaPlayer.create(this,R.raw.wow);
        coinSound.setVolume(1.0f,1.0f);
    }

    private void hideObstaclesAndCoins(){
        for (int i = 0; i < main_IMG_obstacles.length; i++)
            for (int j = 0; j<main_IMG_obstacles[i].length;j++) {
                main_IMG_obstacles[i][j].setVisibility(View.INVISIBLE);
                main_IMG_coins[i][j].setVisibility(View.INVISIBLE);
            }
    }

    private void refreshUI() {
        if(gameManager.isLose()){
            clearObstacleProgress();
            openScoreBoard();
        }
        int[][] currBoardState = gameManager.getBoardState();
        for(int i = 0; i < gameManager.getRows();i++) {
            for(int j = 0; j < gameManager.getCols();j++){
                main_IMG_obstacles[i][j].setVisibility(currBoardState[i][j] == -1 ? View.VISIBLE : View.INVISIBLE);
                main_IMG_coins[i][j].setVisibility(currBoardState[i][j] == 1 ? View.VISIBLE : View.INVISIBLE);
            }
        }
        for (int i = 0; i < main_IMG_cars.length; i++)
            main_IMG_cars[i].setVisibility(gameManager.getCarCurrentLane() == i ?View.VISIBLE :  View.INVISIBLE);
        if(gameManager.isCrashed(getApplicationContext(),v)) {
            if(coinSound.isPlaying()) coinSound.stop();
            if(hitSound.isPlaying()) hitSound.stop();
            hitSound.start();
        }
        if(gameManager.isRewarded(getApplicationContext(),v)) {
            if(coinSound.isPlaying()) coinSound.stop();
            if(hitSound.isPlaying()) hitSound.stop();
            coinSound.start();
        }
        if (gameManager.getCrash() != 0)
            main_IMG_hearts[gameManager.getCrash() -1].setVisibility(View.INVISIBLE);
        main_odometer_text.setText(String.format("%05d", gameManager.getScore()));
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
                int [][] newBoardState = gameManager.getBoardState();
                int cols = gameManager.getCols();
                int rows = gameManager.getRows();

                for (int rowIndex = rows - 1; rowIndex >=0; rowIndex--)
                    for (int colIndex = 0; colIndex< cols;colIndex++) {

                        // -1 = obstacle | 0 = nothing | 1 = coin
                        if(newBoardState[rowIndex][colIndex] != 0) {
                            if(rowIndex != rows - 1){
                                // move obstacle
                                if(newBoardState[rowIndex][colIndex] == -1)
                                    newBoardState[rowIndex + 1][colIndex] = -1;
                                // move coin
                                else
                                    newBoardState[rowIndex + 1][colIndex] = 1;
                            }
                            newBoardState[rowIndex][colIndex] = 0;
                        }
                    }
                gameManager.setDangerousCol(-1);
                gameManager.setCoinCol(-1);
                // if obstacle/coin is on the last row, set it to be the dangerous/coin col
                for (int colIndex = 0; colIndex< cols;colIndex++) {
                    if (newBoardState[rows-1][colIndex] == -1) {
                        gameManager.setDangerousCol(colIndex);
                    }
                    if (newBoardState[rows-1][colIndex] == 1) {
                        gameManager.setCoinCol(colIndex);
                    }
                }
                // after 2 seconds add a new obstacle
                if(tick == 1){
                    tick = 0;
                    int colIndex = getRandomNumber(0,cols);
                    // 0-2 = Obstacle | 3 = Coin
                    int type = getRandomNumber(0,rows);
                    newBoardState[0][colIndex] = type < rows-1 ? -1 : 1;
                }else{
                    tick++;
                }
                gameManager.setBoardState(newBoardState);
                refreshUI();
                gameManager.setScore(gameManager.getScore() + 1);
                ObstacleProgressHandler.postDelayed(ObstacleProgressRunnable, obstacleProgressIntervalMS);
            }
        };
    }

    protected void onPause() {
        super.onPause();
        clearObstacleProgress();
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

    private void openScoreBoard() {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(ScoreActivity.KEY_SCORE,gameManager.getScore());
        intent.putExtra(ScoreActivity.KEY_LOCATION,currentLocation);

        startActivity(intent);
        finish();
    }


    private void findViews() {

        main_odometer_text = findViewById(R.id.main_odometer_text);

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
            findViewById(R.id.main_car03),
            findViewById(R.id.main_car04),
            findViewById(R.id.main_car05),
        };

        main_IMG_obstacles = new ShapeableImageView[][]{
            {
                findViewById(R.id.main_row0_col0_obstacle),
                findViewById(R.id.main_row0_col1_obstacle),
                findViewById(R.id.main_row0_col2_obstacle),
                findViewById(R.id.main_row0_col3_obstacle),
                findViewById(R.id.main_row0_col4_obstacle),
            },
            {
                findViewById(R.id.main_row1_col0_obstacle),
                findViewById(R.id.main_row1_col1_obstacle),
                findViewById(R.id.main_row1_col2_obstacle),
                findViewById(R.id.main_row1_col3_obstacle),
                findViewById(R.id.main_row1_col4_obstacle),
            },
            {
                findViewById(R.id.main_row2_col0_obstacle),
                findViewById(R.id.main_row2_col1_obstacle),
                findViewById(R.id.main_row2_col2_obstacle),
                findViewById(R.id.main_row2_col3_obstacle),
                findViewById(R.id.main_row2_col4_obstacle),
            },
            {
                findViewById(R.id.main_row3_col0_obstacle),
                findViewById(R.id.main_row3_col1_obstacle),
                findViewById(R.id.main_row3_col2_obstacle),
                findViewById(R.id.main_row3_col3_obstacle),
                findViewById(R.id.main_row3_col4_obstacle),
            },
        };

        main_IMG_coins = new ShapeableImageView[][]{
            {
                findViewById(R.id.main_row0_col0_coins),
                findViewById(R.id.main_row0_col1_coins),
                findViewById(R.id.main_row0_col2_coins),
                findViewById(R.id.main_row0_col3_coins),
                findViewById(R.id.main_row0_col4_coins),
            },
            {
                findViewById(R.id.main_row1_col0_coins),
                findViewById(R.id.main_row1_col1_coins),
                findViewById(R.id.main_row1_col2_coins),
                findViewById(R.id.main_row1_col3_coins),
                findViewById(R.id.main_row1_col4_coins),
            },
            {
                findViewById(R.id.main_row2_col0_coins),
                findViewById(R.id.main_row2_col1_coins),
                findViewById(R.id.main_row2_col2_coins),
                findViewById(R.id.main_row2_col3_coins),
                findViewById(R.id.main_row2_col4_coins),
            },
            {
                findViewById(R.id.main_row3_col0_coins),
                findViewById(R.id.main_row3_col1_coins),
                findViewById(R.id.main_row3_col2_coins),
                findViewById(R.id.main_row3_col3_coins),
                findViewById(R.id.main_row3_col4_coins),
            },
        };

    }
}