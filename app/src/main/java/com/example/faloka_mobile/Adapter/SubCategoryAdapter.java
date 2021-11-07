package com.example.faloka_mobile.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.faloka_mobile.API.ApiConfig;
import com.example.faloka_mobile.Model.SubCategory;
import com.example.faloka_mobile.Product.ProductListActivity;
import com.example.faloka_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubCategoryViewHolder> {

    ArrayList<SubCategory> subCategories;
    LayoutInflater inflater;
    Context context;

    public SubCategoryAdapter(Context context, ArrayList<SubCategory> subCategories){
        this.context = context;
        this.subCategories = subCategories;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.grid_subcategory , parent, false);
        return new SubCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        SubCategory subCategory = subCategories.get(position);
        Glide.with(holder.imgSubCategory.getContext() )
                .load(ApiConfig.BASE_IMAGE_URL+subCategory.getPivotSubCategory().getImageURL() )
                .into(holder.imgSubCategory);
        holder.tvSubCategory.setText(subCategory.getName());
        holder.cvSubCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra(SubCategory.EXTRA_SUBCATEGORY, subCategory);
                intent.putExtra("subcategories", subCategories);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imgSubCategory;
        TextView tvSubCategory;
        CardView cvSubCategory;

        public SubCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cvSubCategory = itemView.findViewById(R.id.cv_subcategory);
            imgSubCategory = itemView.findViewById(R.id.img_subcategory);
            tvSubCategory = itemView.findViewById(R.id.tv_subcategory_name);

        }
    }
}
