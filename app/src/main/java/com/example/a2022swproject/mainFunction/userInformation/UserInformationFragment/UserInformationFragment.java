package com.example.a2022swproject.mainFunction.userInformation.UserInformationFragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.AccountActivity;
import com.example.a2022swproject.R;
import com.example.a2022swproject.databinding.FragmentUserinformationBinding;
import com.example.a2022swproject.mainFunction.userInformation.UserInformationViewModel.UserInformationViewModel;

import java.io.IOException;

public class UserInformationFragment extends Fragment {

    private FragmentUserinformationBinding binding;
    private UserInformationViewModel userInformationViewModel;
    private NavController navController;

    private ImageView imgView_userIcon;
    private TextView tv_userName;
    private TextView tv_numberOfPost;
    private TextView tv_numberOfReceived;
    private Button bt_modifyInformation;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userInformationViewModel =
                new ViewModelProvider(this).get(UserInformationViewModel.class);

        binding = FragmentUserinformationBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(UserInformationFragment.this);
        View root = binding.getRoot();

        if(userInformationViewModel.getIngUser() == null){
            Intent intent = new Intent(getActivity(), AccountActivity.class);
            startActivity(intent);
        }

        imgView_userIcon = binding.userInformationIvUserPicture;
        tv_userName = binding.userInformationTvUserName;
        tv_numberOfPost = binding.userInformationTvBoardNumber;
        tv_numberOfReceived = binding.userInformationTvReceiveNumber;
        bt_modifyInformation = binding.userInformationBtModifyInfo;

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        bt_modifyInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_navigation_userInformation_to_navigation_inputUserInformation);
            }
        });

        userInformationViewModel.getUserInformation_VM();


        userInformationViewModel.getDBUser().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                        tv_userName.setText(userInformationViewModel.getUser().getUserName());
                        imgView_userIcon.setImageBitmap(userInformationViewModel.stringToBitmap(userInformationViewModel.getUser().getUserImage()));
                        tv_numberOfPost.setText(Integer.toString(userInformationViewModel.getUser().getNumberOfPost()));
                        tv_numberOfReceived.setText(Integer.toString(userInformationViewModel.getUser().getNumberOfItemReceived()));

                } else {
                    tv_userName.setText(userInformationViewModel.getUserEmail());
                }
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}