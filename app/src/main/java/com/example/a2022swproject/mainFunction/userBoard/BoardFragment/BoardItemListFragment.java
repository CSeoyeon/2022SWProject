package com.example.a2022swproject.mainFunction.userBoard.BoardFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import com.example.a2022swproject.account.model.User;
import com.example.a2022swproject.databinding.FragmentBoarditemlistBinding;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRecycleViewAdapter;
import com.example.a2022swproject.mainFunction.userBoard.BoardViewModel.BoardItemListViewModel;
import com.example.a2022swproject.mainFunction.userBoard.DetailBoardActivity;
import com.example.a2022swproject.mainFunction.userBoard.RecyclerViewInterface;

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
        boardItemListViewModel.getUserInfo_VM();

        boardItemListViewModel.getDBBoard().observe(getViewLifecycleOwner(), new Observer<Boolean>() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(Boolean aBoolean) {

                if(aBoolean){
                    boardItemListViewModel.getDBUser().observe(getViewLifecycleOwner(), new Observer<User>() {
                        @Override
                        public void onChanged(User user) {
                            boardRecycleViewAdapter = new BoardRecycleViewAdapter(boardItemListViewModel.getBoardArrayList(),  user,  recyclerViewInterface);
                            rv_boardView.setAdapter(boardRecycleViewAdapter);
                            rv_boardView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            boardRecycleViewAdapter.notifyDataSetChanged();
                        }
                    });



                }
            }
        });

    }

    final RecyclerViewInterface recyclerViewInterface = new RecyclerViewInterface() {
        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(getContext(), DetailBoardActivity.class);
            Bundle bundle = new Bundle();

            //image는 byte 용량 초과로 보낼 수 없음.

            bundle.putString("boardNumber", boardItemListViewModel.getBoardArrayList().get(position).getBoardNumber());
            bundle.putString("Title", boardItemListViewModel.getBoardArrayList().get(position).getTitle());
            bundle.putString("furniture", boardItemListViewModel.getBoardArrayList().get(position).getFurnitureType());
            bundle.putString("location", boardItemListViewModel.getBoardArrayList().get(position).getLocation());

            intent.putExtras(bundle);


            startActivity(intent);
        }
    };

}
