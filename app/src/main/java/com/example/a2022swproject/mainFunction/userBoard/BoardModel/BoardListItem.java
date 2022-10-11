package com.example.a2022swproject.mainFunction.userBoard.BoardModel;

public class BoardListItem {

    private String title;
    private String userName;


    public BoardListItem(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title){this.title = title;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
