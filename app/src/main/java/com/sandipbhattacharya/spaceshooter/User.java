package com.sandipbhattacharya.spaceshooter;

 public class User {
    private String name;
    private int highScore;

    public User(String name, int highScore) {
        this.name = name;
        this.highScore = highScore;
    }

    public String getName() {
        return name;
    }

    public int getHighScore() {
        return highScore;
    }
}

