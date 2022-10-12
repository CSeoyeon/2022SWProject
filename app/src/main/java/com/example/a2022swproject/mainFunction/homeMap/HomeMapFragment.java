package com.example.a2022swproject.mainFunction.homeMap;

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

import com.example.a2022swproject.databinding.FragmentHomemapBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardFragment.WritingBoardFragment;

public class HomeMapFragment extends Fragment  {

    private FragmentHomemapBinding binding;
    private HomeMapViewModel homeMapViewModel;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeMapViewModel =
                new ViewModelProvider(this).get(HomeMapViewModel.class);

        binding = FragmentHomemapBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(HomeMapFragment.this);



        View root = binding.getRoot();
        return root;
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