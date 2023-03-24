package com.example.a23b_11345b_l01.Models;

import com.example.a23b_11345b_l01.R;

public class Question {

    private String[] answers;
    private String correctAnswer;
    private int imageResource;

    public Question() {
    }

    public String[] getAnswers() {
        return answers;
    }

    public Question setAnswers(String[] answers) {
        this.answers = answers;
        return this;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public Question setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
        return this;
    }

    public int getImageResource() {
        return imageResource;
    }

    public Question setImageResource(int imageResource) {
        this.imageResource = imageResource;
        return this;
    }
}
