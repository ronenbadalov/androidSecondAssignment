package com.example.a23b_11345b_l01.Logic;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.Toast;

import com.example.a23b_11345b_l01.Models.Question;

import java.util.ArrayList;

public class GameManager {

    private final int CORRECT_ANSWER_SCORE = 10;

    private int score;
    private int currentQ;
    private int life;
    private int wrong;

    private ArrayList<Question> questions;

    public GameManager(int life) {
        this.life = life;
        this.score = 0;
        this.currentQ = 0;
        this.wrong = 0;
        questions = DataManager.getQuestions();
    }

    public int getScore() {
        return score;
    }

    public Question getCurrentQuestion() {
        return questions.get(currentQ);
    }

    public int getWrong() {
        return wrong;
    }

    public boolean isGameEnded() {
        return currentQ == questions.size();
    }

    public boolean isLose() {
        return life == wrong;
    }

    public void checkAnswer(Context context, Vibrator v, String selectedAnswer) {
        if (getCurrentQuestion().getCorrectAnswer().equals(selectedAnswer)) { //Correct answer
            score += CORRECT_ANSWER_SCORE;
            Toast.makeText(context, "🥳 Yay!", Toast.LENGTH_LONG).show();
        } else { //wrong answer:
            wrong++;
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                //deprecated in API 26
                v.vibrate(500);
            }
        }
        currentQ++;
    }
}
