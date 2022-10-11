package com.example.a2022swproject;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.a2022swproject.account.fragment.SignInFragment;
import com.example.a2022swproject.databinding.ActivityAccountBinding;
import com.example.a2022swproject.databinding.ActivityMainBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardFragment.BoardItemListFragment;

import java.time.temporal.ValueRange;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

    }

}
