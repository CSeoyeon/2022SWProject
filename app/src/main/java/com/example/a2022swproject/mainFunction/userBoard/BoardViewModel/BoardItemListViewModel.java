package com.example.a2022swproject.mainFunction.userBoard.BoardViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.account.model.UserRepository;
import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.Board;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRepository;

import java.util.ArrayList;

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



}
