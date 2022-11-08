package com.example.a2022swproject.account.model;

public class User {

    private String userEmail = "";
    private String password = "";
    private String userName = "";
    private int numberOfPost = 0;
    private int numberOfItemReceived = 0;
    private String userImage = "";

    public User(){}

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getNumberOfPost() {
        return numberOfPost;
    }

    public void setNumberOfPost(int numberOfPost) {
        this.numberOfPost = numberOfPost;
    }

    public int getNumberOfItemReceived() {
        return numberOfItemReceived;
    }

    public void setNumberOfItemReceived(int numberOfItemReceived) {
        this.numberOfItemReceived = numberOfItemReceived;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}

