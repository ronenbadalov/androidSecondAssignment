package com.example.a23b_11345b_l01.Models;

public class Score {
    private int score = 0;
    private int lan = 0;
    private int lat = 0;

    public Score() {}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLan() {
        return lan;
    }

    public void setLan(int lan) {
        this.lan = lan;
    }

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }
    @Override
    public String toString() {
        return "Score{" +
                "score=" + score +
                ", lan=" + lan +
                ", lat=" + lat +
                '}';
    }
}
