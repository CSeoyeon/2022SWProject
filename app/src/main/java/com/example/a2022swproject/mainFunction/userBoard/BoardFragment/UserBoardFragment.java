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

import com.example.a2022swproject.databinding.FragmentUserboardBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardViewModel.UserBoardViewModel;

public class UserBoardFragment extends Fragment {

    private FragmentUserboardBinding binding;
    private NavController navController;
    private UserBoardViewModel userBoardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserBoardViewModel userBoardViewModel =
                new ViewModelProvider(this).get(UserBoardViewModel.class);

        binding = FragmentUserboardBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(UserBoardFragment.this);


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