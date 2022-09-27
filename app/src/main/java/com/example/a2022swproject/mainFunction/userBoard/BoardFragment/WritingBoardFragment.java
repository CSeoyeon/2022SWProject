package com.example.a2022swproject.mainFunction.userBoard.BoardFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.databinding.FragmentWritingboardBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardViewModel.WritingBoardViewModel;

public class WritingBoardFragment extends Fragment {

    private FragmentWritingboardBinding binding;
    private NavController navController;
    private WritingBoardViewModel writingBoardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        WritingBoardViewModel writingBoardViewModel = new ViewModelProvider(this).get(WritingBoardViewModel.class);
        binding = FragmentWritingboardBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(WritingBoardFragment.this);


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}