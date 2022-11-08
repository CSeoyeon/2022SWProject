package com.example.a2022swproject.mainFunction.userBoard;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2022swproject.R;
import com.example.a2022swproject.databinding.ActivityDetailboardBinding;

public class DetailBoardActivity extends AppCompatActivity{

    private ActivityDetailboardBinding binding;
    private TextView tv_title;
    private TextView tv_furniture;
    private TextView tv_location;
    private ImageView imgView_boardImg;
    private Button bt_takeAFurniture;

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

        //intent로  보낸 정보 받기
        Intent intent = getIntent();
        String title = intent.getStringExtra("Title");




        tv_title.setText(title);

    }
}
