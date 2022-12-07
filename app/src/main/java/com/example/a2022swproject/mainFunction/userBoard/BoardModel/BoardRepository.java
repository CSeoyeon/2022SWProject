package com.example.a2022swproject.mainFunction.userBoard.BoardModel;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.CaptivePortal;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.a2022swproject.account.model.UserRepository;
import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ArrayTable;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class BoardRepository {

    private static BoardRepository INSTANCE = new BoardRepository();
    private FirebaseFirestore foreStore = FirebaseFirestore.getInstance();
    private CollectionReference imageRef = foreStore.collection("tmpImage");
    private CollectionReference boardRef = foreStore.collection("board");

    UserRepository userRepository = UserRepository.getInstance();

    private Board currentBoard;
    private ArrayList<Board> boardList;
    private ArrayList<Board> detailBoardList;
    private BoardRepository() {}

    private String currentBoardNumber = "";

    MarkerInformation markerInformation = new MarkerInformation();

    public static BoardRepository getInstance(){return INSTANCE;}

    //이미지 삽입
    public void writingBoardImg(String boardImageByteString, SingleCallBack<Result<Board>> callback){

        UploadImageSet uploadImageSet = new UploadImageSet();

        uploadImageSet.setImageBitmaptoString(boardImageByteString);
        uploadImageSet.setResult("");

        String boardNumber = userRepository.getUserEmail() + "_" +
                userRepository.getNumberOfPost();


        //후에 document 주소값 boardnumber로 수정
        imageRef.document("tmp")
                .set(uploadImageSet)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onComplete(new Result.Success<String>(boardImageByteString));
                            Log.v("BoardRepository", " : tmpImage Upload Success ");
                        }
                    }
                });

    }

    //get learned FurnitureType
    public void getFurnitureType(SingleCallBack<Result<String>> callBack) throws IOException {
        String boardNumber = userRepository.getUserEmail() + "_" +
                userRepository.getNumberOfPost();

        //후에 document 주소값 boardnumber로 수정
        imageRef.document("tmp").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    callBack.onComplete(new Result.Success<String>((String) snapshot.get("result")));
                    Log.d(TAG, "Current data: " + snapshot.get("result"));
                } else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }


    //게시판 작성
    public void writeBoard(Board board, SingleCallBack<Result<Board>> callback){

        board.setWriterId(userRepository.getUserEmail());
        board.setWriterName(userRepository.getUserName());
        board.setBoardNumber(userRepository.getUserEmail() + "_"+ userRepository.getNumberOfPost());

        markerInformation.setLatitude(Double.parseDouble(board.getLatitude()));
        markerInformation.setLongitude(Double.parseDouble(board.getLongitude()));
        markerInformation.setFurnitureType(board.getFurnitureType());
        markerInformation.setTitle(board.getTitle());
        markerInformation.setBoardNumber(board.getBoardNumber());
        markerInformation.setLocation(board.getLocation());

        //latitude.add(Double.parseDouble(board.getLatitude()));
        //longitude.add(Double.parseDouble(board.getLongitude()));

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

       userRepository.setNumberOfPost(userRepository.getNumberOfPost()+1);
    }

    //delete learned document (firestore)
    public void deleteLearnedImageDocument(){
        imageRef.document("tmp").delete();
    }



    //BoardItemList- 게시판 불러오기
    public void getBoard(SingleCallBack<Result<ArrayList>> callBack){
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

    //DetailBoardActivity- 게시판 불러오기
    public void getDetailBoard(String boardNumber, SingleCallBack<Result<Board>> callBack){
        boardRef.whereEqualTo("boardNumber", boardNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            ArrayList<Board> tmpBoard = new ArrayList<>();
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Board founderBoard = documentSnapshot.toObject(Board.class);
                                tmpBoard.add(founderBoard);
                            }
                            detailBoardList = tmpBoard;
                            callBack.onComplete(new Result.Success<Board>(detailBoardList.get(0)));

                        };
                    }
                });
    }

    //detailBoardActivity - furniture State
    public void setBoardTakingState(String boardNumber, SingleCallBack<Result<Boolean>> callBack){
        DocumentReference documentReference = boardRef.document(boardNumber);
        documentReference.update("takeAFurniture", true)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                callBack.onComplete(new Result.Success<Boolean>(true));
                            }
                        });

    }

    //detailBoardActivity - set furniture Taker
    public void setFurnitureTaker(String boardNumber, String takerID){
        DocumentReference documentReference = boardRef.document(boardNumber);
        documentReference.update("furnitureTaker", takerID)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }


    public String getCurrentBoardNumber() {
        return currentBoardNumber;
    }

    public void setCurrentBoardNumber(String currentBoardNumber) {
        this.currentBoardNumber = currentBoardNumber;
    }

    public MarkerInformation getMarkerInformation() {
        return markerInformation;
    }



}


