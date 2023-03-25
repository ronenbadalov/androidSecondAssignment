package com.example.a23b_11345b_l01.Logic;

public class GameManager {

    private int carCurrentLane;
    private int life;
    private int crash;

    public GameManager(int life) {
        this.life = life;
        this.carCurrentLane = 1;
        this.crash = 0;
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

    public void setCarCurrentLane(int newLane){
        carCurrentLane = newLane;
    }
//    public void checkAnswer(Context context, Vibrator v, String selectedAnswer) {
//        if (getCurrentQuestion().getCorrectAnswer().equals(selectedAnswer)) { //Correct answer
//            score += CORRECT_ANSWER_SCORE;
//            Toast.makeText(context, "🥳 Yay!", Toast.LENGTH_LONG).show();
//        } else { //wrong answer:
//            wrong++;
//            // Vibrate for 500 milliseconds
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
//            } else {
//                //deprecated in API 26
//                v.vibrate(500);
//            }
//        }
//        currentQ++;
//    }
}
