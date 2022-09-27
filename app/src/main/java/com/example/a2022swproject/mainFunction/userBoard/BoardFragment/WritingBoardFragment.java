package com.example.a2022swproject.mainFunction.userBoard.BoardFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.R;
import com.example.a2022swproject.databinding.FragmentWritingboardBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardViewModel.WritingBoardViewModel;

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
        WritingBoardViewModel writingBoardViewModel = new ViewModelProvider(this).get(WritingBoardViewModel.class);
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


                navController.navigate(R.id.action_navigation_userBoard_to_navigation_boarList);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}