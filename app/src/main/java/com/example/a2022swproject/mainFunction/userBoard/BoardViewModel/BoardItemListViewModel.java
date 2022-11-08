package com.example.a2022swproject.mainFunction.userBoard.BoardViewModel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.account.model.UserRepository;
import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.Board;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRepository;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;

public class BoardItemListViewModel extends ViewModel {

    MutableLiveData<Boolean> getDBBoard = new MutableLiveData<>(false);


    private BoardRepository boardRepository = BoardRepository.getInstance();

    private UserRepository userRepository = UserRepository.getInstance();

    private ArrayList<Board> boardArrayList = new ArrayList<>();


    public void getBoardList() {
        boardRepository.getBoard(new SingleCallBack<Result<ArrayList>>() {
            @Override
            public void onComplete(Result<ArrayList> result) {
                if (result instanceof Result.Success) {
                    boardArrayList = ((Result.Success<ArrayList<Board>>) result).getData();
                    getDBBoard.postValue(true);
                }
            }
        });
    }


    public LiveData<Boolean> getDBBoard() {
        return getDBBoard;
    }

    public ArrayList<Board> getBoardArrayList() {
        return boardArrayList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap stringToBitmap(String imgString) {
        Bitmap bitmap = null;
        byte[] bytes = Base64.getDecoder().decode(imgString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        return bitmap;
    }

}
