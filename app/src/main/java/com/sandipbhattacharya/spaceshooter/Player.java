package com.sandipbhattacharya.spaceshooter;

public class Player {
    private String playerId;
    private String name;
    private String email;
    private int age;
    private String password;
    private int highScore;

    public Player() {
        // empty constructor needed for Firebase
    }

    public Player(String name, String email, int age, String password, int highScore) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.password = password;
        this.highScore = highScore;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }
}