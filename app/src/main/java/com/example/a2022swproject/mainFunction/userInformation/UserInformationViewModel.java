package com.example.a2022swproject.mainFunction.userInformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.account.model.User;
import com.example.a2022swproject.account.model.UserRepository;

public class UserInformationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    UserRepository userRepository = UserRepository.getInstance();


    public UserInformationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public String getUserEmail(){
        return userRepository.getUserEmail();
    }


    public LiveData<String> getText() {
        return mText;
    }
}