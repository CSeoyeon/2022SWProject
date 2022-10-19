package com.example.a2022swproject.mainFunction.userBoard.BoardModel;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a2022swproject.account.model.UserRepository;
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
import java.util.ArrayList;

public class BoardRepository {

    private static BoardRepository INSTANCE = new BoardRepository();

    //firebasefirestore -> text, firebaseStorage ->image
    private FirebaseFirestore boardStorage = FirebaseFirestore.getInstance();

    private FirebaseStorage itemStorage = FirebaseStorage.getInstance();
    private StorageReference itemRef = itemStorage.getReference().child("item");

    private FirebaseStorage boardImageStorage = FirebaseStorage.getInstance();
    private StorageReference boardImagesRef = boardImageStorage.getReference().child("boardImages");

    private CollectionReference boardRef = boardStorage.collection("board");

    UserRepository userRepository = UserRepository.getInstance();

    private Board currentBoard;
    private ArrayList<Board> boardList;
    private BoardRepository() {}

    private String currentBoardNumber;

    public static BoardRepository getInstance(){return INSTANCE;}


    //이미지 삽입
    public void writingBoardImg(Bitmap imgBitmap, SingleCallBack<Result<Board>> callback){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        String boardNumber = userRepository.getUserEmail()+ "_"+
                userRepository.getNumberOfPost() +  ".jpg";

        //storage upload - image
        StorageReference uploadRef = itemRef.child(boardNumber);

        //board number 갱신
        userRepository.setNumberOfPost(userRepository.getNumberOfPost()+1);

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


    //게시판 작성
    public void writeBoard(Board board, SingleCallBack<Result<Board>> callback){

        board.setWriterId(userRepository.getUserEmail());
        board.setWriterName(userRepository.getUserName());
        board.setBoardNumber(userRepository.getUserEmail() + "_"+ userRepository.getNumberOfPost());
        userRepository.setNumberOfPost(userRepository.getNumberOfPost()+1);

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
    }

    public void getFurnitureType(SingleCallBack<Result<String>> callBack) throws IOException {
        String txtName = getCurrentBoardNumber();
        Log.v("", "txtName" + txtName);
        //board 이름명에 맞춰 오는지 확인 필수 - currentfileName 변경 해야함.
        StorageReference typeTextRef = boardImagesRef.child("choiseoyeon0223@gmail.com_0.txt");

        File localFile = File.createTempFile("tmp", "txt");

        typeTextRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                try {
                    BufferedReader br = new BufferedReader(new FileReader(localFile));
                    String line = br.readLine();
                    Log.v("boardRepository", "Furniture type: " +line);
                    callBack.onComplete(new Result.Success<String>(line));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }



    //게시판 불러오기
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

    public String getCurrentBoardNumber() {
        return currentBoardNumber;
    }

    public void setCurrentBoardNumber(String currentBoardNumber) {
        this.currentBoardNumber = currentBoardNumber;
    }
}


