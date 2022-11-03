package com.example.a2022swproject.account.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;s
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.a2022swproject.account.model.FindAPasswordViewModel;
import com.example.a2022swproject.databinding.FragmentFindapasswordBinding;

public class FindAPasswordFragment extends Fragment {

    FragmentFindapasswordBinding binding;
    FindAPasswordViewModel findAPasswordViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        findAPasswordViewModel = new ViewModelProvider(this).get(FindAPasswordViewModel.class);
        binding = FragmentFindapasswordBinding.inflate(inflater, container, false);



        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
