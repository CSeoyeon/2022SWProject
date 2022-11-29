package com.example.a2022swproject.mainFunction.homeMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRepository;

import java.util.HashMap;

public class HomeMapViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private BoardRepository boardRepository = BoardRepository.getInstance();
    HashMap<Double, Double> markerLocation = new HashMap<>();

    public HomeMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public HashMap<Double, Double> getMarkerLocation(){

        for(int i =0; i<boardRepository.getLongitude().size(); i++){
            double latitude = boardRepository.getLatitude().get(i);
            double longitude = boardRepository.getLongitude().get(i);
            markerLocation.put(latitude, longitude);
        }

        return markerLocation;
    }

    public LiveData<String> getText() {
        return mText;
    }
}