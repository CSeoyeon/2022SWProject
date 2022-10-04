package com.example.a2022swproject.mainFunction.userBoard.BoardViewModel;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.Board;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRepository;

public class WritingBoardViewModel extends ViewModel {

    private BoardRepository boardRepository = BoardRepository.getInstance();

    private final MutableLiveData<Boolean> writedCompleted = new MutableLiveData<>(false);

    private Board board = new Board();
    private Bitmap imgBitmap;


    public WritingBoardViewModel() {
    }

    public void setBoard(String boardNumber, String writerId, String title, String latitude, String longitude, String address) {
        board.setBoardNumber(boardNumber);
        board.setWriterId(writerId);
        board.setTitle(title);
        board.setLatitude(latitude);
        board.setLongitude(longitude);
        board.setLocation(address);
    }

    //게시글 작성
    public void tryWriting(Board board) {
        boardRepository.writeBoard(board, imgBitmap, new SingleCallBack<Result<Board>>() {
            @Override
            public void onComplete(Result<Board> result) {
                if (result instanceof Result.Success) {
                    Board writingBoard = ((Result.Success<Board>) result).getData();
                    writedCompleted.postValue(true);
                }
            }
        });
    }

    public void setImgBitmap(Bitmap bitmap) {
        this.imgBitmap = bitmap;
    }

    public Board getBoard() {
        return board;
    }

    public LiveData<Boolean> getwritedComplete() {
        return writedCompleted;
    }


}