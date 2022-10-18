package com.example.a2022swproject.account.model;

public class User {

    private String userEmail = "";
    private String password = "";
    private String userName = "";
    private String numberOfPost = "";
    private String numberOfItemReceived = "";

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


    public String getNumberOfPost() {
        return numberOfPost;
    }

    public void setNumberOfPost(String numberOfPost) {
        this.numberOfPost = numberOfPost;
    }

    public String getNumberOfItemReceived() {
        return numberOfItemReceived;
    }

    public void setNumberOfItemReceived(String numberOfItemReceived) {
        this.numberOfItemReceived = numberOfItemReceived;
    }
}

