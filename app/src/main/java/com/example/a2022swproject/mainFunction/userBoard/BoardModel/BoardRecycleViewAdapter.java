package com.example.a2022swproject.mainFunction.userBoard.BoardModel;

import android.view.LayoutInflater;
import android.view.ViewGroup;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ObjectBoarditemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = records.get(position).getTitle();
        holder.tv_title.setText(title);

    }

    @Override
    public int getItemCount() {
        return records.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView tv_title;

        public ViewHolder(ObjectBoarditemBinding binding) {
            super(binding.getRoot());
            tv_title = binding.boardItemTvTitle;

        }
    }
}
