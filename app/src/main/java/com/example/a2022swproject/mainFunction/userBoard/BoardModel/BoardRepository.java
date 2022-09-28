package com.example.a2022swproject.mainFunction.userBoard.BoardModel;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class BoardRepository {

    private static BoardRepository INSTANCE = new BoardRepository();

    //firebasefirestore -> text, firebaseStorage ->image
    private FirebaseFirestore boardStorage = FirebaseFirestore.getInstance();
    private FirebaseStorage boardImgStorage = FirebaseStorage.getInstance();

    private CollectionReference boardRef = boardStorage.collection("board");

    private Board currentBoard;
    private BoardRepository() {}

    public static BoardRepository getInstance(){return INSTANCE;}

    //게시판 작성
    public void writeBoard(Board board, SingleCallBack<Result<Board>> callback){
        boardRef.document(board.getBoardNumber())
                .set(board)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            callback.onComplete(new Result.Success<Board>(board));
                            Log.v("BoardRepository Complete", " : " + BoardRepository.this.toString() );
                        }
                        else{
                            callback.onComplete(new Result.Error(new Exception("BoardRepository : Network call Failed")));
                        }
                    }
                });
    }

}
