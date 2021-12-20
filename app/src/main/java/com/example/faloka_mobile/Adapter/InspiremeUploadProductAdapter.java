package com.example.faloka_mobile.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.R;

import java.util.List;

public class InspiremeUploadProductAdapter extends RecyclerView.Adapter<InspiremeUploadProductAdapter.viewHolder> {

    List<String> images;
    InspiremeProductAdapterListener listener;

    public InspiremeUploadProductAdapter(List<String> images ){
        this.images = images;
    }

    public void setListener(InspiremeProductAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public InspiremeUploadProductAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_inspireme_product, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InspiremeUploadProductAdapter.viewHolder holder, int position) {
        String image = images.get(position);
        holder.bind(image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_inspireme_product);
        }
        public void bind(String image){
            Glide.with(imageView.getContext())
                    .load(ApiConfig.BASE_IMAGE_URL + image)
                    .into(imageView);
        }
    }
    public interface InspiremeProductAdapterListener{
        void move();
    }
}
