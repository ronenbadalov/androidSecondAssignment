package com.example.a23b_11345b_l01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import com.example.a23b_11345b_l01.Logic.GameManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MaterialTextView main_LBL_score;
    private MaterialButton[] main_BTN_options;
    private ShapeableImageView[] main_IMG_hearts;
    private ShapeableImageView main_IMG_flag;

    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        gameManager = new GameManager(main_IMG_hearts.length);
        refreshUI();

        setAnswersClickListeners();
    }

    private void refreshUI() {
        if (gameManager.isGameEnded()) {
            // Winner Screen!
            openScoreScreen("Winner",gameManager.getScore());
        } else if(gameManager.isLose()){
            // Loser Screen!
            openScoreScreen("Game Over!",gameManager.getScore());
        } else {
            main_IMG_flag.setImageResource(gameManager.getCurrentQuestion().getImageResource());
            main_LBL_score.setText("" + gameManager.getScore());
            List<String> answers = Arrays.asList(gameManager.getCurrentQuestion().getAnswers());
            Collections.shuffle(answers);
            for (int i = 0; i < answers.size(); i++)
                main_BTN_options[i].setText(answers.get(i));
            if (gameManager.getWrong() != 0)
                main_IMG_hearts[main_IMG_hearts.length - gameManager.getWrong()].setVisibility(View.INVISIBLE);
        }
    }

    private void openScoreScreen(String status, int score) {
        Intent intent = new Intent(this, ScoreActivity.class);
        intent.putExtra(ScoreActivity.KEY_SCORE,score);
        intent.putExtra(ScoreActivity.KEY_STATUS,status);
        startActivity(intent);
        finish();
    }

    private void setAnswersClickListeners() {
        for (MaterialButton mb: main_BTN_options) {
            mb.setOnClickListener(v -> clicked(mb.getText().toString()));
        }
    }

    private void clicked(String selectedAnswer) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gameManager.checkAnswer(getApplicationContext(), v, selectedAnswer);
        refreshUI();
    }


    private void findViews() {
        main_LBL_score = findViewById(R.id.main_LBL_score);
        main_BTN_options = new MaterialButton[]{
                findViewById(R.id.main_BTN_option1),
                findViewById(R.id.main_BTN_option2),
                findViewById(R.id.main_BTN_option3),
                findViewById(R.id.main_BTN_option4)};
        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)};
        main_IMG_flag = findViewById(R.id.main_IMG_flag);
    }
}