package com.example.a2022swproject.mainFunction.userBoard.BoardModel;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a2022swproject.databinding.ObjectBoarditemBinding;

import java.util.ArrayList;

public class BoardRecycleViewAdapter extends Adapter<BoardRecycleViewAdapter.ViewHolder> {

    private ArrayList<Board> records;

    public BoardRecycleViewAdapter(ArrayList<Board> items) {
        this.records = items;
    }

    @NonNull
    @Override
    public BoardRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoardRecycleViewAdapter.ViewHolder(ObjectBoarditemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = records.get(position).getTitle();
        holder.tv_title.setText(title);

        //holder.tv_title.setText("ewtwerterraeteat");

    }

    @Override
    public int getItemCount() {
        return records.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView iv_userIcon;
        protected TextView tv_userName;
        protected TextView tv_title;
        protected ImageView iv_boardImage;

        public ViewHolder(@NonNull ObjectBoarditemBinding binding) {
            super(binding.getRoot());
            iv_userIcon = binding.boardItemIvUserIcon;
            tv_userName = binding.boardItemTvUserName;
            tv_title = binding.boardItemTvTitle;
            iv_boardImage = binding.boardItemIvBoardImage;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
