package com.example.a2022swproject.mainFunction.userBoard.BoardViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class WritingBoardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public WritingBoardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}