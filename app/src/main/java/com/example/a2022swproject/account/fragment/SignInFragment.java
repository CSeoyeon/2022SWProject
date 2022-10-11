package com.example.a2022swproject.account.fragment;

import android.os.Bundle;
import android.util.Log;
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
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.R;
import com.example.a2022swproject.account.viewmodel.AuthViewModel;
import com.example.a2022swproject.databinding.FragmentSigninBinding;
import com.google.firebase.auth.FirebaseUser;

public class SignInFragment extends Fragment {

    private FragmentSigninBinding binding;
    private AuthViewModel authViewModel;
    private NavController navController;

    private EditText et_email;
    private EditText et_password;
    private Button bt_signIn;
    private Button bt_signUp;
    private Button bt_findPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this , (ViewModelProvider.Factory) ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
        authViewModel.getUserData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    //계정이 없을 때
                    //navController.navigate(R.id.action_signInFragment_to_signOutFragment);
                    Log.v("signinFragment", "계정없음");
                }
            }
        });
        
        

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FragmentSigninBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(SignInFragment.this);

        et_email = binding.SignInEtEmail;
        et_password = binding.SignInEtPassword;
        bt_signIn = binding.SignInBtSignIn;
        bt_signUp = binding.signInBtSignUp;
        bt_findPassword = binding.SignInBtFindPassword;

        return binding.getRoot();
        
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        bt_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()){
                    Log.v("sign in ","성공");
                    authViewModel.signIn(email , password);
                }
            }
        });

        bt_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_navigation_signIn_to_navigation_signUp);
            }
        });


    }
}
