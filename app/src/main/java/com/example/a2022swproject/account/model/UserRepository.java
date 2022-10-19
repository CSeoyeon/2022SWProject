package com.example.a2022swproject.account.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.Board;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.a2022swproject.account.model.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository INSTANCE = new UserRepository();

    private FirebaseFirestore userStorage = FirebaseFirestore.getInstance();
    private FirebaseStorage userIconImgStorage = FirebaseStorage.getInstance();
    private CollectionReference usersRef = userStorage.collection("user");

    private User currUser;

    private String userEmail;
    private String userPassword;
    private String userName;
    private int numberOfPost = 0;

    private UserRepository(){}
    public static UserRepository getInstance(){return INSTANCE;}


    public void setUserInformation(User user, Bitmap imgBitmap, SingleCallBack<Result<User>> callback){

        //input a user information
        usersRef.document(userEmail)
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            callback.onComplete(new Result.Success<User>(currUser));
                            Log.v("BoardRepository Complete", " : " + UserRepository.this.toString() );
                        }
                        else{
                            callback.onComplete(new Result.Error(new Exception("UserRepository : Network call Failed")));
                        }
                    }
                });

        //input a user icon
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        StorageReference uploadRef = userIconImgStorage.getReference().child("userIconImg/"+ userEmail +".jpg");
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

    public void getUserInformation(SingleCallBack<Result<User>> callback){

        usersRef.whereEqualTo("userEmail", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                           for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                               User foundUser = documentSnapshot.toObject(User.class);
                               currUser = foundUser;
                               setUserName(currUser.getUserName());
                               callback.onComplete(new Result.Success<User>(currUser));
                           }
                        }
                        else{
                            callback.onComplete(new Result.Error(new Exception("failed get a userInformation")));
                        }
                    }
                });

    }

    public Bitmap getUserIcon() throws IOException {

        StorageReference downloadRef = userIconImgStorage.getReference().child("userIconImg/" + getUserEmail() + ".jpg");
        File localFile = File.createTempFile("userIcon", "jpg");
        final Bitmap[] userIconBitmap = new Bitmap[1];

        downloadRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                Bitmap tmpBitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                userIconBitmap[0] = tmpBitmap;
                // Local temp file has been created
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        return userIconBitmap[0];

    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getNumberOfPost() {
        return numberOfPost;
    }

    public void setNumberOfPost(int numberOfPost) {
        this.numberOfPost = numberOfPost;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}



