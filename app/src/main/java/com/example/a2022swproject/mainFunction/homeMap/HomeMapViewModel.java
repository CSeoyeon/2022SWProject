package com.example.a2022swproject.mainFunction.homeMap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRepository;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.MarkerInformation;
import com.naver.maps.map.overlay.Marker;

import java.util.HashMap;

public class HomeMapViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private BoardRepository boardRepository = BoardRepository.getInstance();
    HashMap<Double, Double> markerLocation = new HashMap<>();
    private MarkerInformation markerInformation = new MarkerInformation();

    public HomeMapViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public MarkerInformation registerMarkerInformation(){
        markerInformation = boardRepository.getMarkerInformation();

        return markerInformation;
    }

    public LiveData<String> getText() {
        return mText;
    }
}