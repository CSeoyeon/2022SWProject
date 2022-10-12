package com.example.a2022swproject.account.model;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;

public class UserRepository {
    private static UserRepository INSTANCE = new UserRepository();

    private FirebaseFirestore userStorage = FirebaseFirestore.getInstance();
    private FirebaseStorage userIconImgStorage = FirebaseStorage.getInstance();

    private User currUser;

    private String userEmail;
    private String userPassword;

    private UserRepository(){}
    public static UserRepository getInstance(){return INSTANCE;}


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
