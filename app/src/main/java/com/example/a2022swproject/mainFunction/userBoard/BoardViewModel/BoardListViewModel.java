package com.example.a2022swproject.mainFunction.userBoard.BoardViewModel;

import androidx.lifecycle.ViewModel;

import com.example.a2022swproject.account.model.UserRepository;
import com.example.a2022swproject.mainFunction.userBoard.BoardModel.BoardRepository;

public class BoardListViewModel extends ViewModel {
    private BoardRepository boardRepository = BoardRepository.getInstance();

    private UserRepository userRepository = UserRepository.getInstance();

    public String getUser(){
        return userRepository.getUserEmail();
    }

}
