package com.example.a2022swproject.mainFunction.userInformation.UserInformationViewModel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.account.model.User;
import com.example.a2022swproject.account.model.UserRepository;
import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;

public class InputUserInformationViewModel extends ViewModel {

    MutableLiveData<Boolean> setUserDB = new MutableLiveData<>(false);

    private UserRepository userRepository = UserRepository.getInstance();

    private User user = new User();
    private Bitmap imgBitmap;

    public void setUserInformation(String email, String name) {
        user.setUserEmail(email);
        user.setUserName(name);
    }

    public void tryWritingInformation(User user) {
        userRepository.setUserInformation(user, imgBitmap, new SingleCallBack<Result<User>>() {
            @Override
            public void onComplete(Result<User> result) {
                if (result instanceof Result.Success) {
                    User writingUserInformation = ((Result.Success<User>) result).getData();
                    setUserDB.postValue(true);
                }
            }
        });
    }


    public void setImgBitmap(Bitmap imgBitmap) {
        this.imgBitmap = imgBitmap;
    }

    public LiveData<Boolean> setDBBoard() {
        return setUserDB;
    }

    public User getUser(){
        return user;
    }

    public String getUserEmail(){
        return userRepository.getUserEmail();
    }
}