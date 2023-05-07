package com.example.a23b_11345b_l01.Models;

public class Score {
    private int score = 0;
    private double lan = 0;
    private double lat = 0;

    public Score() {}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getLan() {
        return lan;
    }

    public void setLan(double lan) {
        this.lan = lan;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
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
