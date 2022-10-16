package com.example.a2022swproject.mainFunction.userInformation.UserInformationFragment;

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
import com.example.a2022swproject.mainFunction.userInformation.UserInformationViewModel.UserInformationViewModel;

public class UserInformationFragment extends Fragment {

    private FragmentUserinformationBinding binding;
    private UserInformationViewModel userInformationViewModel;
    private NavController navController;

    private ImageView imgView_userIcon;
    private TextView tv_userName;
    private TextView tv_numberOfPost;
    private TextView tv_numberOfReceived;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
       userInformationViewModel =
                new ViewModelProvider(this).get(UserInformationViewModel.class);

        binding = FragmentUserinformationBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(UserInformationFragment.this);
        View root = binding.getRoot();

        imgView_userIcon = binding.userInformationIvUserPicture;
        tv_userName = binding.userInformationTvUserName;
        tv_numberOfPost = binding.userInformationTmptvBoardNumber;
        tv_numberOfReceived = binding.userInformationTmptvReceiveNumber;


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_userName.setText(userInformationViewModel.getUserEmail());


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}