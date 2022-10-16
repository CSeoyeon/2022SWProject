package com.example.a2022swproject.account.model;

import android.graphics.Bitmap;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.example.a2022swproject.account.model.User;

import java.io.ByteArrayOutputStream;

public class UserRepository {
    private static UserRepository INSTANCE = new UserRepository();

    private FirebaseFirestore userStorage = FirebaseFirestore.getInstance();
    private FirebaseStorage userIconImgStorage = FirebaseStorage.getInstance();

    private User currUser;

    private String userEmail;
    private String userPassword;
    private String name;
    private String phoneNumber;

    private UserRepository(){}
    public static UserRepository getInstance(){return INSTANCE;}


    public void setUserInformation(User user, Bitmap imgBitmap, SingleCallBack<Result<User>> callback){

        currUser.setUserName(user.getUserName());
        currUser.setPhoneNumber(user.getPhoneNumber());

        //input a user information
        userStorage.document(user.getUserEmail())
                .set(currUser)
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


        //이미지 삽입
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        //boardNumber 수정 해야함
        StorageReference uploadRef = userIconImgStorage.getReference().child("userIconImg/"+ phoneNumber +".jpg");
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
}
