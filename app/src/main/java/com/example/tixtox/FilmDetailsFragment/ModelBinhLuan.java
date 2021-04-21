package com.example.tixtox.FilmDetailsFragment;

public class ModelBinhLuan {
    private String userID;
    private String txtName;
    private long txtTime;
    private String txtContent;

    public ModelBinhLuan() {
    }

    public ModelBinhLuan(String userID, String txtName, long txtTime, String txtContent) {
        this.userID = userID;
        this.txtName = txtName;
        this.txtTime = txtTime;
        this.txtContent = txtContent;
    }

    public String getUserID() {
        return userID;
    }

    public long getTxtTime() {
        return txtTime;
    }

    public String getTxtName() {
        return txtName;
    }

    public String getTxtContent() {
        return txtContent;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
