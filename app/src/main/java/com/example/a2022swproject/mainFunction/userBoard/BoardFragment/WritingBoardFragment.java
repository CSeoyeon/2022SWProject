package com.example.a2022swproject.mainFunction.userBoard.BoardFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.MainActivity;
import com.example.a2022swproject.R;
import com.example.a2022swproject.databinding.FragmentWritingboardBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardViewModel.WritingBoardViewModel;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.overlay.Marker;

public class WritingBoardFragment extends Fragment {

    private FragmentWritingboardBinding binding;
    private NavController navController;
    private WritingBoardViewModel writingBoardViewModel;

    private EditText et_title;
    private EditText et_location;
    private ImageButton imgBtn_addImg;
    private Button bt_writing;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        writingBoardViewModel = new ViewModelProvider(this).get(WritingBoardViewModel.class);
        binding = FragmentWritingboardBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(WritingBoardFragment.this);

        et_title = binding.writingBoardEtTitle;
        et_location = binding.writingBoardEtLocation;
        imgBtn_addImg = binding.writingBoardImgbtnAddImg;
        bt_writing = binding.writingBoardBtWriting;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //add image
        ActivityResultLauncher<Intent> launchGallery = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>()
                {
                    @Override
                    public void onActivityResult(ActivityResult result)
                    {
                        if(result.getResultCode() == Activity.RESULT_OK)
                        {
                            Uri selectedImage = result.getData().getData();
                            imgBtn_addImg.setImageURI(selectedImage);
                            writingBoardViewModel.setImgBitmap(((BitmapDrawable)imgBtn_addImg.getDrawable()).getBitmap());
                            imgBtn_addImg.invalidate();
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
                String boardNumber = "1";
                String writerId = "1";

                String title = et_title.getText().toString();
                String latitude =  String.valueOf(((MainActivity)getActivity()).getLatitude());
                String longitude = String.valueOf(((MainActivity)getActivity()).getLongitude());

                writingBoardViewModel.setBoard(boardNumber, writerId, title, latitude, longitude);
                writingBoardViewModel.tryWriting(writingBoardViewModel.getBoard());

                writingBoardViewModel.getwritedComplete().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        ((MainActivity)getActivity()).setMarker();
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