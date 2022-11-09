package com.example.a2022swproject.mainFunction.userBoard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2022swproject.CodeDetailFunction.BitmapTypeCasting;
import com.example.a2022swproject.R;
import com.example.a2022swproject.databinding.ActivityDetailboardBinding;
import com.example.a2022swproject.mainFunction.Result;
import com.example.a2022swproject.mainFunction.SingleCallBack;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.Board;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;

public class DetailBoardActivity extends AppCompatActivity{

    private ActivityDetailboardBinding binding;
    private FirebaseFirestore boardStorage = FirebaseFirestore.getInstance();
    private CollectionReference boardRef = boardStorage.collection("board");

    private BitmapTypeCasting bitmapTypeCasting = new BitmapTypeCasting();

    private TextView tv_title;
    private TextView tv_furniture;
    private TextView tv_location;
    private ImageView imgView_boardImg;
    private Button bt_takeAFurniture;

    private Board currentBoard;
    private String boardNumber;
    private String title;
    private String location;
    private String furniture;
    private String imgBitmapString;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailboard);



        binding = ActivityDetailboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tv_title = binding.detailBoardTvTitle;
        tv_furniture = binding.detailBoardTvFurniture;
        tv_location = binding.detailBoardTvLocation;
        imgView_boardImg = binding.detailBoardIvBoardImage;
        bt_takeAFurniture = binding.detailBoardBtTakeAFurniture;

        //BoarditemListFramgent에서 intent로 보낸 정보 받기
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        if(extras != null){
            boardNumber = extras.getString("boardNumber");
        }
        else{
            Log.v("Detail Board Activity", "bundle empty");
        }


        // 받은 boardNumber 로 직접 board import
        // 메소드로 만들고 board객체만 빼내야하는데...매칭이 안 되고 null값만 나와서 이렇게..
        boardRef.whereEqualTo("boardNumber", boardNumber)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Board founderBoard = documentSnapshot.toObject(Board.class);
                                currentBoard = founderBoard;

                                title = currentBoard.getTitle();
                                imgBitmapString = currentBoard.getBoardImageByte();
                                furniture = currentBoard.getFurnitureType();
                                location = currentBoard.getLocation();

                                tv_title.setText(title);
                                imgView_boardImg.setImageBitmap(bitmapTypeCasting.stringToBitmap(currentBoard.getBoardImageByte()));
                                tv_furniture.setText(furniture);
                                tv_location.setText(location);

                            }
                        };
                    }
                });




        bt_takeAFurniture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //take a Furniture (false에서 true로 번경)
                finish();


                //board item List에서 완료된 글이라 뜨기



            }
        });

    }

}
