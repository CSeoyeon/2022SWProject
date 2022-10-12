package com.example.a2022swproject.mainFunction.userInformation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.databinding.FragmentUserinformationBinding;
import com.example.a2022swproject.mainFunction.homeMap.HomeMapFragment;

public class UserInformationFragment extends Fragment {

    private FragmentUserinformationBinding binding;
    private UserInformationViewModel userInformationViewModel;
    private NavController navController;

    private ImageView userIcon;
    private TextView userName;
    private TextView numberOfPost;
    private TextView numberOfReceived;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       userInformationViewModel =
                new ViewModelProvider(this).get(UserInformationViewModel.class);

        binding = FragmentUserinformationBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(UserInformationFragment.this);
        View root = binding.getRoot();

        userIcon = binding.userInformationIvUserPicture;
        userName = binding.userInformationTvUserName;
        numberOfPost = binding.userInformationTmptvBoardNumber;
        numberOfReceived = binding.userInformationTmptvReceiveNumber;


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userName.setText(userInformationViewModel.getUserEmail());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}