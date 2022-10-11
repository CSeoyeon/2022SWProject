package com.example.a2022swproject.account.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.R;
import com.example.a2022swproject.account.viewmodel.AuthViewModel;
import com.example.a2022swproject.databinding.FragmentSigninBinding;
import com.example.a2022swproject.databinding.FragmentSignupBinding;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {

    private FragmentSignupBinding binding;
    private AuthViewModel authViewModel;
    private NavController navController;

    private EditText et_email;
    private EditText et_password;
    private EditText et_passwordConfirm;
    private Button bt_signUp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this , (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        authViewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    navController.navigate(R.id.action_navigation_signUp_to_navigation_signIn);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(SignUpFragment.this);

        et_email = binding.signUpEtEmail;
        et_password = binding.signUpEtPasswrod;
        et_passwordConfirm = binding.signUpEtConfirmPassword;
        bt_signUp = binding.signUpBtSignUp;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        bt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    authViewModel.register(email , password);
                    //옵저버로 등록 확인 할 수 있도록 추가 해야함
                    navController.navigate(R.id.action_navigation_signUp_to_navigation_signIn);
                }
            }
        });

    }
}
