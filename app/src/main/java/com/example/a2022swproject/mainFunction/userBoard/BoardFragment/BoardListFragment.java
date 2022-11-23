package com.example.a2022swproject.mainFunction.userBoard.BoardFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2022swproject.AccountActivity;
import com.example.a2022swproject.R;
import com.example.a2022swproject.databinding.FragmentBoardlistBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardViewModel.BoardListViewModel;

public class BoardListFragment extends Fragment {

    private FragmentBoardlistBinding binding;
    private NavController navController;
    private BoardListViewModel boardListViewModel;

    private Button bt_writingBoard;
    private FrameLayout frameLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        boardListViewModel = new ViewModelProvider(this).get(BoardListViewModel.class);
        binding = FragmentBoardlistBinding.inflate(inflater, container, false);
        navController = NavHostFragment.findNavController(BoardListFragment.this);

        if(boardListViewModel.getUser() == null ){
            Intent intent = new Intent(getActivity(), AccountActivity.class);
            startActivity(intent);
        }


        bt_writingBoard = binding.boardListBtWritingBoard;
        frameLayout = binding.boardListFrameLayout;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bt_writingBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_navigation_boardList_to_navigation_userBoard);
            }
        });

        //board list
        frameLayout.setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        BoardItemListFragment boardItemListFragment = new BoardItemListFragment();
        fragmentTransaction.replace(R.id.boardList_frameLayout, boardItemListFragment).commit();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
