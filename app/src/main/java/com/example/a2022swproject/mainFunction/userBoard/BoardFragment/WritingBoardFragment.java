package com.example.a2022swproject.mainFunction.userBoard.BoardFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

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
    private ImageButton imgBtn_addImag;
    private Button bt_writing;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        writingBoardViewModel = new ViewModelProvider(this).get(WritingBoardViewModel.class);
        binding = FragmentWritingboardBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(WritingBoardFragment.this);

        et_title = binding.writingBoardEtTitle;
        et_location = binding.writingBoardEtLocation;
        imgBtn_addImag = binding.writingBoardImgbtnAddImg;
        bt_writing = binding.writingBoardBtWriting;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //이미지 추가
        imgBtn_addImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //작성버튼
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