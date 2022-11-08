package com.example.a2022swproject.mainFunction.userBoard.BoardModel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.example.a2022swproject.databinding.ObjectBoarditemBinding;
import com.example.a2022swproject.mainFunction.userBoard.RecyclerViewInterface;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;

public class BoardRecycleViewAdapter extends Adapter<BoardRecycleViewAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private ArrayList<Board> records;


    public BoardRecycleViewAdapter(ArrayList<Board> items, RecyclerViewInterface recyclerViewInterface) {
        this.records = items;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public BoardRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BoardRecycleViewAdapter.ViewHolder(
                ObjectBoarditemBinding.inflate
                        (LayoutInflater.from(parent.getContext()), parent, false), recyclerViewInterface);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String title = records.get(position).getTitle();
        String writerName = records.get(position).getWriterName();
        String ImgBitmap = records.get(position).getBoardImageByte();

        holder.tv_title.setText(title);
        holder.tv_userName.setText(writerName);
        holder.iv_boardImage.setImageBitmap(stringToBitmap(ImgBitmap));

    }

    @Override
    public int getItemCount() {
        return records.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView iv_userIcon;
        protected TextView tv_userName;
        protected TextView tv_title;
        protected ImageView iv_boardImage;

        public ViewHolder(@NonNull ObjectBoarditemBinding binding, RecyclerViewInterface recyclerViewInterface) {
            super(binding.getRoot());
            iv_userIcon = binding.boardItemIvUserIcon;
            tv_userName = binding.boardItemTvUserName;
            tv_title = binding.boardItemTvTitle;
            iv_boardImage = binding.boardItemIvBoardImage;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (recyclerViewInterface != null) {
                            recyclerViewInterface.onItemClick(position);
                        }
                    }
                }
            });

        }


        @Override
        public String toString() {
            return super.toString();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap stringToBitmap(String imgString) {
        Bitmap bitmap = null;
        byte[] bytes = Base64.getDecoder().decode(imgString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
        return bitmap;
    }
}
