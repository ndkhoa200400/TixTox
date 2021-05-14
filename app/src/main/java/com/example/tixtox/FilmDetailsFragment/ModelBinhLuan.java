package com.example.tixtox.FilmDetailsFragment;

import java.util.Date;

public class ModelBinhLuan {
    private String userID;
    private String username;
    private long cmtTime;
    private String cmtContnt;
    private float ratingScore;

    public ModelBinhLuan() {
    }

    public ModelBinhLuan(String userID, String username, String cmtContnt, float ratingScore) {
        this.userID = userID;
        this.username = username;
        this.cmtTime = new Date().getTime();
        this.cmtContnt = cmtContnt;
        this.ratingScore = ratingScore;
    }

    public String getUserID() {
        return userID;
    }

    public long getCmtTime() {
        return cmtTime;
    }

    public String getUsername() {
        return username;
    }

    public String getCmtContnt() {
        return cmtContnt;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setRatingScore(float ratingScore) {
        this.ratingScore = ratingScore;
    }

    public float getRatingScore() {
        return ratingScore;
    }
}
