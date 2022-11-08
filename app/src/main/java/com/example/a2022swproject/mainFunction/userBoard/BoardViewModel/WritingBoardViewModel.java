package com.example.a2022swproject.mainFunction.userBoard.BoardViewModel;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.account.model.User;
import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.Board;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class WritingBoardViewModel extends ViewModel {

    private BoardRepository boardRepository = BoardRepository.getInstance();

    private final MutableLiveData<Boolean> writingCompleted = new MutableLiveData<>(false);

    private MutableLiveData<Boolean> putImage = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> furniture = new MutableLiveData<>(false);

    private User user = new User();


    private Board board = new Board();
    private Bitmap imgBitmap;
    private String furnitureType = "";

    public WritingBoardViewModel() {
    }

    public void setBoard( String title, String latitude, String longitude, String address) {
        board.setTitle(title);
        board.setLatitude(latitude);
        board.setLongitude(longitude);
        board.setLocation(address);
    }

    public void addBitmapFromBoard(Board board, String bitmapToString){
        board.setBoardImageByte(bitmapToString);
    }

    //게시글 작성
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void tryWriting(Board board) {
        addBitmapFromBoard(board, bitmapToString(imgBitmap));

        boardRepository.writeBoard(board, new SingleCallBack<Result<Board>>() {
            @Override
            public void onComplete(Result<Board> result) {
                if (result instanceof Result.Success) {
                    Board writingBoard = ((Result.Success<Board>) result).getData();
                    writingCompleted.postValue(true);
                }
            }
        });
    }
    
    public void tryWritingImg(){
        boardRepository.writingBoardImg(imgBitmap, new SingleCallBack<Result<Board>>() {
            @Override
            public void onComplete(Result<Board> result) {
                if(result instanceof Result.Success){
                    putImage.postValue(true);
                }
            }
        });
    }

    public void getFurnitureType_MV() throws IOException {
        if(furnitureType.equals("")){}
        boardRepository.getFurnitureType(new SingleCallBack<Result<String>>() {
            @Override
            public void onComplete(Result<String> result) {
                if(result instanceof Result.Success){
                    furnitureType = ((Result.Success<String>) result).getData();
                    furniture.postValue(true);
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

    public void setImgBitmap(Bitmap bitmap) {
        this.imgBitmap = bitmap;
    }

    public Board getBoard() {
        return board;
    }

    public LiveData<Boolean> getWritingComplete() {
        return writingCompleted;
    }

    public LiveData<Boolean> putWritingImg(){
        return putImage;
    }

    public LiveData<Boolean> getFurniture(){return furniture;}

    public String getFurnitureType(){
        return furnitureType;
    }

    public void setFurnitureType(String furnitureType) {
        this.furnitureType = furnitureType;
    }
}