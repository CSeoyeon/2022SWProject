package com.example.a2022swproject.mainFunction.userInformation.UserInformationViewModel;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.account.model.User;
import com.example.a2022swproject.account.model.UserRepository;
import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class InputUserInformationViewModel extends ViewModel {

    MutableLiveData<Boolean> setUserDB = new MutableLiveData<>(false);

    private UserRepository userRepository = UserRepository.getInstance();

    private User user = new User();
    private Bitmap imgBitmap;
    private String imgBitmapToString;

    public void setUserInformation(String email, String name) {
        user.setUserEmail(email);
        user.setUserName(name);
    }


    public void addBitmapFromUserInformation(User user, String bitmapToString){
        user.setUserImage(bitmapToString);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void tryWritingInformation(User user) {
        addBitmapFromUserInformation(user, bitmapToString(imgBitmap));

        userRepository.setUserInformation(user, new SingleCallBack<Result<User>>() {
            @Override
            public void onComplete(Result<User> result) {
                if (result instanceof Result.Success) {
                    User writingUserInformation = ((Result.Success<User>) result).getData();
                    userRepository.setUserName(writingUserInformation.getUserName());
                    setUserDB.postValue(true);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String bitmapToString(Bitmap bitmap){
        String img = "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        img = Base64.getEncoder().encodeToString(bytes);
        return img;
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