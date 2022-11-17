package com.example.a2022swproject.mainFunction.userBoard.BoardModel;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class Board {

    private String boardNumber;
    private String writerId;

    private String writerName;
    private String title;
    private String latitude;
    private String longitude;
    private String location;
    private String furnitureType = "checking Furniture";
    private String boardImageByte;
    private boolean takeAFurniture = false;

    public Board(){}

    public String getBoardNumber() {
        return boardNumber;
    }

    public void setBoardNumber(String boardNumber) {
        this.boardNumber = boardNumber;
    }

    public String getWriterId() {
        return writerId;
    }

    public void setWriterId(String writerId) {
        this.writerId = writerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public String getWriterName() {
        return writerName;
    }

    public void setWriterName(String writerName) {
        this.writerName = writerName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFurnitureType() {
        return furnitureType;
    }

    public void setFurnitureType(String furnitureType) {
        this.furnitureType = furnitureType;
    }

    public String getBoardImageByte() {
        return boardImageByte;
    }

    public void setBoardImageByte(String boardImageByte) {
        this.boardImageByte = boardImageByte;
    }

    public boolean isTakeAFurniture() {
        return takeAFurniture;
    }

    public void setTakeAFurniture(boolean takeAFurniture) {
        this.takeAFurniture = takeAFurniture;
    }



    @NonNull
    @Override
    public String toString() {
        return "Board{" +
                "BoardNumber=" + boardNumber +"/n" + ","+
                "writerId=" + writerId +"/n" + ","+
                "title=" + title +"/n" + ","+
                "latitude=" +latitude +"/n" + ","+
                "longitude=" + longitude;
    }
}
