package com.example.a2022swproject.mainFunction.userBoard.BoardModel;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ArrayTable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class BoardRepository {

    private static BoardRepository INSTANCE = new BoardRepository();

    //firebasefirestore -> text, firebaseStorage ->image
    private FirebaseFirestore boardStorage = FirebaseFirestore.getInstance();
    private FirebaseStorage boardImgStorage = FirebaseStorage.getInstance();

    private CollectionReference boardRef = boardStorage.collection("board");

    private Board currentBoard;
    private ArrayList<Board> boardList;
    private BoardRepository() {}

    public static BoardRepository getInstance(){return INSTANCE;}

    //게시판 작성
    public void writeBoard(Board board, Bitmap imgBitmap, SingleCallBack<Result<Board>> callback){
        //글 삽입
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

        //이미지 삽입
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        //boardNumber 수정 해야함
        StorageReference uploadRef = boardImgStorage.getReference().child("boardImages/boardNumber_2" + ".jpg");
        UploadTask uploadTask = uploadRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });



    }

    //게시판 불러오기
    public void getBoard(SingleCallBack<Result<Board>> callBack){
        boardRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<Board> tmpBoard = new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Board board = documentSnapshot.toObject(Board.class);
                                tmpBoard.add(board);
                            }
                            boardList = tmpBoard;
                            callBack.onComplete(new Result.Success<ArrayList<Board>>(boardList));

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}
