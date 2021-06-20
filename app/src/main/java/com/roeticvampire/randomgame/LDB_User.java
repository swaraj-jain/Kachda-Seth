package com.roeticvampire.randomgame;

public class LDB_User {
    private String profileImg;
    private String name;
    private int score;

    public LDB_User(String name, String profileImg,  int score) {
        this.profileImg = profileImg;
        this.name = name;
        this.score = score;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
