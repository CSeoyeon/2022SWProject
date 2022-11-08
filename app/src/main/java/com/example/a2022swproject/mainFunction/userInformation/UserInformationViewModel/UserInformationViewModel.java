package com.example.a2022swproject.mainFunction.userInformation.UserInformationViewModel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.account.model.User;
import com.example.a2022swproject.account.model.UserRepository;
import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;

import java.io.ByteArrayInputStream;
import java.io.IOError;
import java.io.IOException;
import java.util.Base64;

public class UserInformationViewModel extends ViewModel {

    MutableLiveData<Boolean> getDBUser = new MutableLiveData<>(false);

    UserRepository userRepository = UserRepository.getInstance();
    private User user= new User();

    public UserInformationViewModel() {}

    public void getUserInformation_VM(){
        userRepository.getUserInformation(new SingleCallBack<Result<User>>() {
            @Override
            public void onComplete(Result<User> result) {
                if (result instanceof Result.Success){
                    user = ((Result.Success<User>)result).getData();
                    getDBUser.postValue(true);
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap stringToBitmap(String imgString){
        Bitmap bitmap = null;
        byte[] bytes = Base64.getDecoder().decode(imgString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        return bitmap;
    }


    public String getUserEmail(){
        return userRepository.getUserEmail();
    }

    public User getUser(){
        return user;
    }

    public LiveData<Boolean> getDBUser() {
        return getDBUser;
    }
}