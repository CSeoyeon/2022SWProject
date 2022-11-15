package com.example.a2022swproject.mainFunction.userBoard.BoardFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.MainActivity;
import com.example.a2022swproject.R;
import com.example.a2022swproject.databinding.FragmentWritingboardBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardViewModel.WritingBoardViewModel;
import com.naver.maps.map.overlay.Marker;

import java.io.IOException;

public class WritingBoardFragment extends Fragment {

    private FragmentWritingboardBinding binding;
    private NavController navController;
    private WritingBoardViewModel writingBoardViewModel;


    private EditText et_title;
    private EditText et_location;
    private Button bt_locationFind;
    private ImageButton imgBtn_addImg;
    private Button bt_writing;
    private TextView tv_checkFurniture;
    private NestedScrollView scrollView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        writingBoardViewModel = new ViewModelProvider(this).get(WritingBoardViewModel.class);
        binding = FragmentWritingboardBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(WritingBoardFragment.this);

        et_title = binding.writingBoardEtTitle;
        et_location = binding.writingBoardEtLocation;
        bt_locationFind = binding.writingBoardBtLocationFind;
        imgBtn_addImg = binding.writingBoardImgbtnAddImg;
        bt_writing = binding.writingBoardBtWriting;
        tv_checkFurniture = binding.writingBoardTvCheckFurniture;
        scrollView = binding.writingBoardScroll;
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        tv_checkFurniture.setText("사진을 선택해주세요");

        writingBoardViewModel.getFurniture().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                try {
                    writingBoardViewModel.getFurnitureType_MV();
                    tv_checkFurniture.setText(writingBoardViewModel.getFurnitureType());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        Log.v("boardFragment", "Furniture type: " + writingBoardViewModel.getFurnitureType());

        //get String address
        bt_locationFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_location.setText("");
                et_location.setText(
                        ((MainActivity) getActivity()).getAddress()
                );
            }
        });


        //add image
        ActivityResultLauncher<Intent> launchGallery = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Uri selectedImage = result.getData().getData();
                            imgBtn_addImg.setImageURI(selectedImage);
                            writingBoardViewModel.setImgBitmap(((BitmapDrawable) imgBtn_addImg.getDrawable()).getBitmap());
                            imgBtn_addImg.invalidate();

                            //가구 들어가서 파일 확인 및 textview 수정
                            writingBoardViewModel.tryWritingImg();

                        }
                    }
                });

        imgBtn_addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                launchGallery.launch(intent);
            }
        });


        //writing
        bt_writing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String title = et_title.getText().toString();
                String latitude = String.valueOf(((MainActivity) getActivity()).getLatitude());
                String longitude = String.valueOf(((MainActivity) getActivity()).getLongitude());
                String address = ((MainActivity) getActivity()).getAddress();

                writingBoardViewModel.setBoard(title, latitude, longitude, address);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    writingBoardViewModel.tryWriting(writingBoardViewModel.getBoard());
                }


                writingBoardViewModel.getWritingComplete().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {

                        ((MainActivity) getActivity()).setMarker(
                                ((MainActivity) getActivity()).getLatitude(), ((MainActivity) getActivity()).getLongitude()
                        );

                        navController.navigate(R.id.action_navigation_userBoard_to_navigation_boarList);
                    }
                });

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}