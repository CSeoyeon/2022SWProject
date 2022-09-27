package com.example.a2022swproject.mainFunction.userInformation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserInformationViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public UserInformationViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}