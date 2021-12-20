package com.example.faloka_mobile.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Model.InspireMe;
import com.example.faloka_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class AccountPostAdapter extends RecyclerView.Adapter<AccountPostAdapter.ViewHolder> {
    List<InspireMe> inspireMeList = new ArrayList<>();

    public AccountPostAdapter(List<InspireMe> inspireMeList){
        this.inspireMeList = inspireMeList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_account_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InspireMe inspireMe = inspireMeList.get(position);
        holder.bind(inspireMe);
    }

    @Override
    public int getItemCount() {
        return inspireMeList.size();


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_account_post);
        }
        public void bind(InspireMe inspireMe){
            Glide.with(imageView).
                    load(ApiConfig.BASE_IMAGE_URL + inspireMe.getImageUrl())
                    .into(imageView);


        }
    }
}
