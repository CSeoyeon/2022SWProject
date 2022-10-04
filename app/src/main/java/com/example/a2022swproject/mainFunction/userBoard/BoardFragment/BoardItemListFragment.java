package com.example.a2022swproject.mainFunction.userBoard.BoardFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2022swproject.databinding.FragmentBoarditemlistBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.Board;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardListItem;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRecycleViewAdapter;
import com.example.a2022swproject.mainFunction.userBoard.BoardViewModel.BoardItemListViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

//BoardList 안에 들어가는 게시글 목록 화면
public class BoardItemListFragment extends Fragment {

    FragmentBoarditemlistBinding binding;
    BoardItemListViewModel boardItemListViewModel;
    
    private BoardRecycleViewAdapter boardRecycleViewAdapter;
    private RecyclerView rv_boardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        boardItemListViewModel = new ViewModelProvider(this).get(BoardItemListViewModel.class);
        binding = FragmentBoarditemlistBinding.inflate(inflater, container, false);
        rv_boardView = binding.boardItemListRvBoardList;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        boardItemListViewModel.getBoardList();

        boardItemListViewModel.getDBBoard().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    boardRecycleViewAdapter = new BoardRecycleViewAdapter(boardItemListViewModel.getBoardArrayList());

                    rv_boardView.setAdapter(boardRecycleViewAdapter);
                    rv_boardView.setLayoutManager(new LinearLayoutManager(requireContext()));
                    boardRecycleViewAdapter.notifyDataSetChanged();
                }
            }
        });

    }
}