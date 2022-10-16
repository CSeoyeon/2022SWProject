package com.example.a2022swproject.mainFunction.userInformation.UserInformationFragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

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

import com.example.a2022swproject.R;
import com.example.a2022swproject.databinding.FragmentInputuserinformationBinding;
import com.example.a2022swproject.mainFunction.userInformation.UserInformationViewModel.InputUserInformationViewModel;

import org.w3c.dom.Text;

public class InputUserInformationFragment extends Fragment {

    private com.example.a2022swproject.databinding.FragmentInputuserinformationBinding binding;
    private InputUserInformationViewModel inputUserInformationViewModel;
    private NavController navController;

    private ImageButton imgBtn_addIcon;
    private TextView tv_email;
    private EditText et_name;
    private EditText et_phoneNumber;
    private Button bt_save;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        inputUserInformationViewModel = new ViewModelProvider(this).get(InputUserInformationViewModel.class);
        binding = FragmentInputuserinformationBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(InputUserInformationFragment.this);
        View root = binding.getRoot();

        imgBtn_addIcon = binding.inputUserInformationImgBtnSetIcon;
        tv_email = binding.inputUserInformationTvEmail;
        et_name = binding.inputUserInformationEtName;
        et_phoneNumber = binding.inputUserInformationEtPhoneNumber;

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_email.setText(inputUserInformationViewModel.getUserEmail());

        inputUserInformationViewModel.setUserInformation(et_name.getText().toString(), et_phoneNumber.getText().toString());

        //add image icon
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
                            imgBtn_addIcon.setImageURI(selectedImage);
                            inputUserInformationViewModel.setImgBitmap(((BitmapDrawable)imgBtn_addIcon.getDrawable()).getBitmap());
                            imgBtn_addIcon.invalidate();
                        }
                    }
                });

        imgBtn_addIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                launchGallery.launch(intent);

            }
        });

        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                inputUserInformationViewModel.setDBBoard().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        navController.navigate(R.id.action_navigation_inputUserInformation_to_navigation_signIn);

                    }
                });

            }
        });



    }



}
